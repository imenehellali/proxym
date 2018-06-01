package com.example.hella.proxym;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;

import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


public class Mainmenu extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener
         {

    private TabLayout main_menu_tabs;
    private ImageButton inventory_button, social_button, skill_button;
    private Context _content=this;


    private GoogleMap mMap;

    private GoogleApiClient _client;
    private LocationRequest _locationRequest;
    private Location _lastLocation;
    private Marker _currentLocationMArker;
    private Circle _currentLocationRange;

    public static final int PERMISSION_REQUEST_LOCATION_CODE = 99;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //initialize
        main_menu_tabs = (TabLayout) findViewById(R.id.main_menu_tabs);

        main_menu_tabs.addTab(main_menu_tabs.newTab(),0);
        main_menu_tabs.addTab(main_menu_tabs.newTab(),1);
        main_menu_tabs.addTab(main_menu_tabs.newTab(),2);

        main_menu_tabs.getTabAt(0).setIcon(R.drawable.inventory).setText("");
        main_menu_tabs.getTabAt(1).setIcon(R.drawable.inventory).setText("");
        main_menu_tabs.getTabAt(2).setIcon(R.drawable.social).setText("");

        main_menu_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:{
                        startActivity(new Intent(_content, Inventory.class));
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(_content, Skill.class));
                        break;
                    }
                    case 2:{
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            //we can add other permission later

            case PERMISSION_REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (_client == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }
    protected synchronized void buildGoogleApiClient() {
        _client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        _client.connect();
    }

    //Todo add collectables with every change respawn while moving
    @Override
    public void onLocationChanged(Location location) {

        _lastLocation = location;

        if (_currentLocationMArker != null) {
            _currentLocationMArker.remove();
        }
        LatLng _latLang = new LatLng(location.getLatitude(), location.getLongitude());

        //todo there is a stoke we need to get rid of
        CircleOptions _circleOptions=new CircleOptions();
        _circleOptions.center(_latLang);
        _circleOptions.radius(1000f);
        _circleOptions.fillColor(R.color.colorPrimary);
        _circleOptions.strokeWidth(0f);

        _currentLocationRange= mMap.addCircle(_circleOptions);

        MarkerOptions _markerOptions = new MarkerOptions();
        _markerOptions.position(_latLang);
        _markerOptions.title("current location");
        _markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        _currentLocationMArker = mMap.addMarker(_markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(_latLang));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));

        //stop updating location i will removed later on
        if (_client != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(_client, this);
        }

    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        _locationRequest = new LocationRequest();
        _locationRequest.setInterval(1000);
        _locationRequest.setFastestInterval(1000);
        _locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(_client, _locationRequest, this);
        }

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION_CODE);
            }
        }

        return false;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

