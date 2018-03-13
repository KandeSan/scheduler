package org.kandesun.directionservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;
import org.kandesun.directionservice.direction.DirectionsControl;

public class DirectionsMainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        DirectionsControl control = new DirectionsControl(this);
        control.getDirection(TravelMode.TRANSIT, "倉敷駅", "草津温泉", new DateTime(2018, 3, 20, 10, 0));
        //control.getDirection(TravelMode.TRANSIT, "倉敷駅", "岡山駅", new DateTime(2018, 3, 20, 10, 0));
    }
}


