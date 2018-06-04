package com.example.hella.proxym;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.graphics.Color;
import android.location.Location;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class Mainmenu extends AppCompatActivity implements OnMapReadyCallback {

    private TabLayout main_menu_tabs;
    private Context _content = this;


    private GoogleMap mMap;
    private static final String TAG = "Mainmenu";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15;

    private boolean _permissionGranted = false;

    private FusedLocationProviderClient fusedLocationProviderClient;

    //todo idee: vchange the mode_button (3 on each other : 1 visible 2,3 gone -> onClick 2 visible 1,3 gone .... and they instantiate a corresponding class constructor and can only call its functions and stuff  )

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        if (isServiceOK())
            getLocationPermission();

        //initialize
        main_menu_tabs = (TabLayout) findViewById(R.id.main_menu_tabs);

        main_menu_tabs.addTab(main_menu_tabs.newTab(), 0);
        main_menu_tabs.addTab(main_menu_tabs.newTab(), 1);
        main_menu_tabs.addTab(main_menu_tabs.newTab(), 2);

        main_menu_tabs.getTabAt(0).setIcon(R.drawable.inventory).setText("");
        main_menu_tabs.getTabAt(1).setIcon(R.drawable.skill).setText("");//todo change icon
        main_menu_tabs.getTabAt(2).setIcon(R.drawable.social).setText("");

        main_menu_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: {
                        startActivity(new Intent(_content, Inventory.class));
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(_content, Skill.class));
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(_content, Social.class));
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void moveCamera(LatLng latLng, float ZOOM) {

        Toast.makeText(this, "position " + latLng, Toast.LENGTH_SHORT).show();
        mMap.addMarker(new MarkerOptions().position(latLng).title("current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.addCircle(new CircleOptions().center(latLng).radius(1000f).strokeWidth(0f).fillColor(Color.argb(0.2f,0f, 142f,255f)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));
    }

    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (_permissionGranted) {
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(_content, "SUCCESS current location", Toast.LENGTH_LONG).show();
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);

                            LatLng spawn=new CollectorScreen().spawn(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                            //todo icons -> BitmaoDescriptorFactory.fromResource... doesn'T work -> ERROR : image must be bitmap Oo
                            mMap.addMarker(new MarkerOptions().position(spawn).title("Bomb").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                            Toast.makeText(_content, "spawn position " + spawn, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(_content, "ERROR current location", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public boolean isServiceOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Mainmenu.this);
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Mainmenu.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
            return false;
        }
        Toast.makeText(this, "map request denied", Toast.LENGTH_LONG).show();
        return false;
    }

    private void getLocationPermission() {
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                _permissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "map initialized", Toast.LENGTH_LONG).show();
        mMap = googleMap;
        if (_permissionGranted) {
            getCurrentLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        _permissionGranted=false;
        switch(requestCode){
            case LOCATION_REQUEST_CODE:{
                if(grantResults.length>0){
                    for(int i=0; i<grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            _permissionGranted=false;
                            return;
                        }
                    }
                    _permissionGranted=true;
                    initMap();
                }
            }
        }
    }

}

