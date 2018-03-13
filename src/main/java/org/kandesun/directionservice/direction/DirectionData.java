package org.kandesun.directionservice.direction;

/**
 * Created on 2018/02/22.
 */

public class DirectionData
{
    private MapInfo mapInfo;
    private DirectionInfo directionInfo;
    private String directionPolyline;
    private String[] warnings;

    public MapInfo getMapInfo()
    {
        return mapInfo;
    }

    public void setMapInfo(MapInfo mapInfo)
    {
        this.mapInfo = mapInfo;
    }

    public DirectionInfo getDirectionInfo()
    {
        return directionInfo;
    }

    public void setDirectionInfo(DirectionInfo directionInfo)
    {
        this.directionInfo = directionInfo;
    }

    public String getDirectionPolyline()
    {
        return directionPolyline;
    }

    public void setDirectionPolyline(String directionPolyline)
    {
        this.directionPolyline = directionPolyline;
    }

    public String[] getWarnings()
    {
        return warnings;
    }

    public void setWarnings(String[] warnings)
    {
        this.warnings = warnings;
    }
}
