package org.kandesun.directionservice.direction;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;

/**
 * Created on 2018/02/18.
 */

public class DetailDirection
{
    // 交通手段
    private TravelMode travelMode;

    // 出発時間
    private DateTime departureTime;

    // 到着時間
    private DateTime arrivalTime;

    // 経過時間
    private long durationTime;

    // 距離
    private long distance;

    // 開始位置
    private LatLng startLocation;

    // 終点位置
    private LatLng endLocation;

    // 説明
    private String description;

    // ポリライン
    private String polyLine;

    // 公共交通機関　詳細
    private TransitDetailInfo transitDetailInfo;

    public DetailDirection(TravelMode travelMode, DateTime departureTime, DateTime arrivalTime, long durationTime)
    {
        this.travelMode = travelMode;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.durationTime = durationTime;
    }

    public TravelMode getTravelMode()
    {
        return travelMode;
    }

    public void setTravelMode(TravelMode travelMode)
    {
        this.travelMode = travelMode;
    }

    public DateTime getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(DateTime departureTime)
    {
        this.departureTime = departureTime;
    }

    public DateTime getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(DateTime arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }


    public long getDurationTime()
    {
        return durationTime;
    }

    public void setDurationTime(long durationTime)
    {
        this.durationTime = durationTime;
    }

    public long getDistance()
    {
        return distance;
    }

    public void setDistance(long distance)
    {
        this.distance = distance;
    }

    public LatLng getStartLocation()
    {
        return startLocation;
    }

    public void setStartLocation(LatLng location)
    {
        this.startLocation = location;
    }

    public LatLng getEndLocation()
    {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation)
    {
        this.endLocation = endLocation;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPolyLine()
    {
        return polyLine;
    }

    public void setPolyLine(String polyLine)
    {
        this.polyLine = polyLine;
    }

    public TransitDetailInfo getTransitDetailInfo()
    {
        return transitDetailInfo;
    }

    public void setTransitDetailInfo(TransitDetailInfo transitDetailInfo)
    {
        this.transitDetailInfo = transitDetailInfo;
    }

    public String getDepartureString()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return simpleDateFormat.format(departureTime.toDate());
    }

    public String getArrivalString()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        return simpleDateFormat.format(arrivalTime.toDate());
    }

    public String toDirectionInfo()
    {
        String travelModeStr = "Start/End";
        if(travelMode != null)
        {
            travelModeStr = travelMode.toString();

            if(travelMode.equals(TravelMode.TRANSIT))
            {
                travelModeStr = transitDetailInfo.getType().type.toString();
            }
        }

        return travelModeStr + " From " + getDepartureString() + " To " + getArrivalString();
    }
}
