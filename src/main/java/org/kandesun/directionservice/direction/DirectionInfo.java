package org.kandesun.directionservice.direction;

import com.google.maps.model.LatLng;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/02/18.
 */

public class DirectionInfo
{
    // 出発時間
    private DateTime departureTime;

    // 出発地
    private String departurePlace;

    // 出発位置
    private LatLng departureLocation;

    // 到着時間
    private DateTime arrivalTime;

    // 到着地
    private String arrivalPlace;

    // 到着位置
    private LatLng arrivalLocation;

    private List<DetailDirection> detailDirectionList = new ArrayList<>();

    public DirectionInfo(String departurePlace, DateTime departureTime, LatLng departureLocation,
                         String arrivalPlace, DateTime arrivalTime, LatLng arrivalLocation)
    {
        this.departurePlace = departurePlace;
        this.departureTime = departureTime;
        this.departureLocation = departureLocation;
        this.arrivalPlace = arrivalPlace;
        this.arrivalTime = arrivalTime;
        this.arrivalLocation = arrivalLocation;

        DetailDirection detailDirection = new DetailDirection(null, departureTime, departureTime, 0);
        detailDirection.setDistance(0L);
        detailDirection.setStartLocation(departureLocation);
        detailDirection.setEndLocation(departureLocation);
        detailDirectionList.add(detailDirection);
    }

    public void goal()
    {
        DetailDirection detailDirection = new DetailDirection(null,arrivalTime, arrivalTime, 0);
        detailDirectionList.add(detailDirection);
    }

    public DateTime getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(DateTime arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public DateTime getDepartureTime()
    {
        return departureTime;
    }

    public void setDepartureTime(DateTime departureTime)
    {
        this.departureTime = departureTime;
    }

    public DetailDirection getDetailDirection(int index)
    {
        return detailDirectionList.get(index);
    }

    public List<DetailDirection> getDetailDirectionList()
    {
        return detailDirectionList;
    }

    public DetailDirection getNewestDetailDirection()
    {
        int length = detailDirectionList.size();
        return detailDirectionList.get(length-1);
    }

    public void addDetailDirection(DetailDirection detailDirection)
    {
        detailDirectionList.add(detailDirection);
    }

}
