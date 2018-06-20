package com.example.hella.proxym;



import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Iterator;


public class CollectorScreen {

    int number=1;
    public ArrayList<Collectables> collected=new ArrayList<Collectables>();


    public CollectorScreen() {

    }


    //works just fine
    public void add(Collectables element){
        int i=0;
        if(!collected.isEmpty()){
            for (Iterator<Collectables> it = collected.iterator(); it.hasNext(); ) {
                Collectables collectables = it.next();
                if(collectables.id.equals(element.id)){
                    element.number++;
                    Log.d("CollectorScreen", " "+i+" "+ element.id+"\n"+element.number);
                    return;
                }//found it in array and added it -> so quit function
                i++;
            }
            collected.add(i, element);
            Log.d("CollectorScreen", " "+i+" "+ element.id);
            return;
        }// didn't find it in array so add new one
        else{
            collected.add(i, element);
            Log.d("CollectorScreen", " "+i+" "+ element.id);
            return;
        }//first element

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