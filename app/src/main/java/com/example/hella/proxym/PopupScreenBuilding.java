package com.example.hella.proxym;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class PopupScreenBuilding extends AppCompatActivity {

    private int width, height;
    private ImageView tower,mill,minery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.popupscreen_building);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
        height = dm.heightPixels;

        width*=0.75;
        width-=width/4;

        height*=0.05;
        //height-=height/2;

        getWindow().setLayout((int) width, (int) height);

        tower=(ImageView)findViewById(R.id.tower);
        mill=(ImageView)findViewById(R.id.mill);
        minery=(ImageView)findViewById(R.id.minery);

        tower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PopupScreenBuilding.this, Mainmenu.class);
                intent.putExtra("building_clicked", "tower");
                finish();
            }
        });
        mill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PopupScreenBuilding.this, Mainmenu.class);
                intent.putExtra("building_clicked", "mill");
                finish();
            }
        });
        minery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PopupScreenBuilding.this, Mainmenu.class);
                intent.putExtra("building_clicked", "minery");
                finish();
            }
        });
    }
}

