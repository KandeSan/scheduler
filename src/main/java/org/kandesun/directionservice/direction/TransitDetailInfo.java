package org.kandesun.directionservice.direction;

import com.google.maps.model.TransitDetails;
import com.google.maps.model.Vehicle;

/**
 * Created on 2018/02/19.
 */

public class TransitDetailInfo
{
    private TransitDetails transitDetails;

    public TransitDetailInfo(TransitDetails transitDetails)
    {
        this.transitDetails = transitDetails;
    }

    public Vehicle getType()
    {
        return transitDetails.line.vehicle;
    }
}
