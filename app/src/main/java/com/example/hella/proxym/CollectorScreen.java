package com.example.hella.proxym;



import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Iterator;


public class CollectorScreen {
    public CollectorScreen() {

    }
    public LatLng spawn(LatLng currentLocation) {
        float Min=-0.02f;
        float Max=0.02f;
        float longitude= (float) (currentLocation.longitude+(Min+(Math.random()*(Max-Min))));
        float latitude= (float) (currentLocation.latitude+(Min+(Math.random()*(Max-Min))));
        LatLng _spawn=new LatLng(latitude,longitude);
        return _spawn;
    }


}