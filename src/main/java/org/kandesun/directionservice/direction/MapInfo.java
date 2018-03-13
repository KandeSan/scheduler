package org.kandesun.directionservice.direction;


import com.google.maps.model.LatLng;

/**
 * Created on 2018/02/22.
 */

public class MapInfo
{
    private LatLng northEast;
    private LatLng southWest;

    private String copyright;

    public MapInfo(LatLng northEast, LatLng southWest, String copyright)
    {
        this.northEast = northEast;
        this.southWest = southWest;
    }

    public LatLng getNorthEast()
    {
        return northEast;
    }

    public LatLng getSouthWest()
    {
        return southWest;
    }

    public String getCopyright()
    {
        return copyright;
    }
}
