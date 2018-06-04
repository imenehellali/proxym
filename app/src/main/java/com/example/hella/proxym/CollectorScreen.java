package com.example.hella.proxym;


import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.model.LatLng;



public class CollectorScreen {
    private ImageButton mode_button;

    public CollectorScreen() {
    }

    public LatLng spawn(LatLng currentLocation) {
        float Min=0.001f;
        float Max=0.03f;
        float longitude= (float) (currentLocation.longitude+(Min+(Math.random()*(Max-Min))));
        float latitude= (float) (currentLocation.latitude+(Min+(Math.random()*(Max-Min))));
        LatLng _spawn=new LatLng(latitude,longitude);
        return _spawn;
    }

    //todo finish the change icon + change class to work on + add same thing in MainMenu
   /* public void changeModeButton(final ImageButton imageButton){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(R.id.){
                    case R.drawable.collectmode:{
                        imageButton.setImageResource(R.drawable.buildmode);
                    }
                }

            }
        });
    }
*/
}
