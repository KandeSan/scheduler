package org.kandesun.directionservice.direction;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.Bounds;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.Duration;
import com.google.maps.model.TransitDetails;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created on 2018/02/18.
 */

public class DirectionsControl
{
    private GeoApiContext context;

    public DirectionsControl(Context application)
    {
        try
        {
            ApplicationInfo appInfo = application.getPackageManager().getApplicationInfo(application.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = appInfo.metaData;
            context = new GeoApiContext.Builder().apiKey(bundle.getString("com.google.android.geo.API_KEY")).build();
        }
        catch(PackageManager.NameNotFoundException ex )
        {
            Log.e(ex.getLocalizedMessage(), Log.getStackTraceString(ex));
        }
        catch(Exception ex)
        {
            Log.e(ex.getLocalizedMessage(), Log.getStackTraceString(ex));
        }
    }

    public void getDirection(TravelMode mode, String origin, String destination, DateTime arrivalDate)
    {
        try
        {
            DirectionsApi.newRequest(context).mode(mode).origin(origin).destination(destination).arrivalTime(arrivalDate).setCallback(new PendingResult.Callback<DirectionsResult>()
            {
                @Override
                public void onResult(final DirectionsResult result)
                {

                    if(result == null || result.routes.length < 1)
                    {
                        Log.e("Error", "Result Is Null");
                        return;
                    }

                    Log.i("Success", "Success");
                    parseResult(result);

                }

                @Override
                public void onFailure(Throwable e)
                {
                    Log.e(e.getLocalizedMessage(), Log.getStackTraceString(e));
                }
            });
        }
        catch(Exception ex)
        {
            Log.e(ex.getLocalizedMessage(), Log.getStackTraceString(ex));
        }

    }

    protected void parseResult(final DirectionsResult result)
    {

        DirectionsRoute[] routes = result.routes.clone();

        DirectionData directionService = new DirectionData();

        // routes[]
        for(DirectionsRoute route : routes)
        {
            // 地図情報
            Bounds bounds = route.bounds;
            MapInfo mapInfo = new MapInfo(bounds.northeast, bounds.southwest, route.copyrights);
            directionService.setMapInfo(mapInfo);

            // ポリライン
            directionService.setDirectionPolyline(route.overviewPolyline.getEncodedPath());

            // 注意表示
            directionService.setWarnings(route.warnings);

            // legs[]
            DirectionsLeg[] legs = route.legs;
            for(DirectionsLeg leg : legs)
            {
                // 道順オブジェクト作成
                DirectionInfo directionInfo = new DirectionInfo(leg.startAddress, leg.departureTime, leg.startLocation,
                        leg.endAddress, leg.arrivalTime, leg.endLocation);
                directionService.setDirectionInfo(directionInfo);

                // steps[]
                DirectionsStep[] steps = leg.steps;
                for(DirectionsStep step : steps)
                {
                    DetailDirection current = directionInfo.getNewestDetailDirection();

                    // 移動手段を取得
                    TravelMode travelMode = step.travelMode;

                    // 経過時間を取得
                    Duration duration = step.duration;
                    long durationSeconds = duration.inSeconds;

                    DetailDirection nextDirection;
                    switch(travelMode)
                    {
                    case WALKING:
                        // 徒歩

                        // 徒歩の場合、到着時間=出発時間=前のポイントの到着時間+経過時間
                        // 経過時間
                        DateTime arrivalTime = current.getArrivalTime();
                        DateTime nextArrivalTime = arrivalTime.plus(durationSeconds);

                        nextDirection = new DetailDirection(step.travelMode, nextArrivalTime, nextArrivalTime, durationSeconds);

                        break;
                    case TRANSIT:
                        // 公共交通機関

                        // 公共交通機関の場合、出発時間=現在のポイントのtransit_detailの出発時間
                        // 到着時間=現在のポイントのtransit_detailの到着時間
                        // 経過時間=経過時間
                        TransitDetails transitDetails = step.transitDetails;
                        nextDirection = new DetailDirection(step.travelMode, transitDetails.departureTime, transitDetails.arrivalTime, durationSeconds);
                        nextDirection.setTransitDetailInfo(new TransitDetailInfo(transitDetails));

                        break;
                    default:
                        nextDirection = new DetailDirection(step.travelMode, null, null, -1);
                        break;
                    }

                    nextDirection.setDistance(step.distance.inMeters);
                    nextDirection.setStartLocation(step.startLocation);
                    nextDirection.setEndLocation(step.endLocation);
                    nextDirection.setDescription(step.htmlInstructions);
                    nextDirection.setPolyLine(step.polyline.getEncodedPath());

                    directionInfo.addDetailDirection(nextDirection);


                }

                directionInfo.goal();

                List<DetailDirection> detailDirections =  directionInfo.getDetailDirectionList();
                for(DetailDirection detailDirection : detailDirections)
                {
                    Log.i("Root", detailDirection.toDirectionInfo());
                }
            }
        }

    }
}
