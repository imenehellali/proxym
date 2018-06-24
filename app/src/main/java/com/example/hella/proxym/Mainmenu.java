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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hella.proxym.Util.UserProfile;
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
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Mainmenu extends AppCompatActivity implements OnMapReadyCallback{


    private TabLayout main_menu_tabs;

    private Context _content;
    private ImageButton mode_button;
    private int counter;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;

    private UserProfile TUP;
    private UserProfile TUP_OTHER;
    private UserProfile TUP_NOW;
    private ArrayList<String> materials;
    private  ArrayList<String> equipments;


    public GoogleMap mMap;
    public Location currentLocation;
    public  static CollectorScreen collectorScreen;
    BuilderScreen builderScreen;
    FighterScreen fighterScreen;



    private static final String TAG = "Mainmenu";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15;

    private boolean _permissionGranted = false;

    private FusedLocationProviderClient fusedLocationProviderClient;

    //UserProfile here everything data -LatLang - materials - collectables after sign Up
    //UserProfile here everything +LAtLang + material + collectables after siGn in


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


        if (isServiceOK())
            getLocationPermission();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        mRef=firebaseDatabase.getReference(user.getUid());

        TUP_NOW=new UserProfile();

        TUP=(UserProfile) Mainmenu.this.getIntent().getExtras().getParcelable("USERPROFILE2");
        if(TUP.getUsermaterials()==null){
            TUP.setUsermaterials(new ArrayList<String>());
        }else if(TUP.getUsermaterials()!=null){
            TUP_NOW.setUsermaterials(TUP.getUsermaterials());
        }
        if(TUP.getUserequipments()==null){
            TUP.setUserequipments(new ArrayList<String>());
        }else if(TUP.getUserequipments()!=null){
            TUP_NOW.setUserequipments(TUP.getUserequipments());
        }

        if(Mainmenu.this.getIntent().getExtras().getParcelable("OTHERUSER")!=null){
            TUP_OTHER=(UserProfile) Mainmenu.this.getIntent().getExtras().getParcelable("OTHERUSER");
        }

        _content = this;

        collectorScreen = new CollectorScreen();
        builderScreen = new BuilderScreen();
        fighterScreen = new FighterScreen();

        materials=new ArrayList<String>();
        equipments=new ArrayList<String>();

        main_menu_tabs = (TabLayout) findViewById(R.id.main_menu_tabs);

        main_menu_tabs.addTab(main_menu_tabs.newTab(), 0);
        main_menu_tabs.addTab(main_menu_tabs.newTab(), 1);
        main_menu_tabs.addTab(main_menu_tabs.newTab(), 2);

        main_menu_tabs.getTabAt(0).setIcon(R.drawable.inventory).setText("");
        main_menu_tabs.getTabAt(1).setIcon(R.drawable.skill).setText("");
        main_menu_tabs.getTabAt(2).setIcon(R.drawable.social).setText("");

        //initialize
        counter=1;
        mode_button=(ImageButton) findViewById(R.id.mode_button);
        if(TUP_OTHER!=null){
            mode_button.setVisibility(View.GONE);
        }



        if (mMap!=null && currentLocation!=null) {
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    switch (marker.getTitle()) {
                        case "Bomb": {
                            TUP_NOW.addMaterials("Bomb ");
                            marker.remove();
                            break;
                        }
                        case "Leaf": {

                            TUP_NOW.addMaterials("Leaf ");
                            marker.remove();
                            break;
                        }
                        case "Animal Resource": {
                            TUP_NOW.addMaterials("AnimalResource ");
                            marker.remove();
                            break;
                        }
                        case "Iron": {
                            TUP_NOW.addMaterials("Iron ");
                            marker.remove();
                            break;
                        }
                    }
                    return false;

                }
            });
        }

        main_menu_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final String lat=String.valueOf(currentLocation.getLatitude());
                final String lng=String.valueOf(currentLocation.getLongitude());
                TUP_NOW.setUserlng(lng);
                TUP_NOW.setUserlat(lat);
                materials=TUP_NOW.getUsermaterials();
                equipments=TUP_NOW.getUserequipments();
                switch (tab.getPosition()) {
                    //after each select it updates the position to the database
                    case 0: {
                        UserProfile userProfile=new UserProfile(TUP.getUseremail(),TUP.getUserpassword(),TUP.getUserprofilepic(),TUP.getUserstatus(),TUP.getUsername(),TUP.getUseravatarpic(),TUP.getUseravatarname(),lat,lng,materials,equipments);
                        startActivity(new Intent(_content,Inventory.class).putExtra("USERPROFILE3",userProfile));
                        mRef.setValue(userProfile);
                        break;
                    }
                    case 1: {
                        UserProfile userProfile=new UserProfile(TUP.getUseremail(),TUP.getUserpassword(),TUP.getUserprofilepic(),TUP.getUserstatus(),TUP.getUsername(),TUP.getUseravatarpic(),TUP.getUseravatarname(),lat,lng,materials,equipments);
                        startActivity(new Intent(_content, Skill.class).putExtra("USERPROFILE3",userProfile));
                        mRef.setValue(userProfile)
                        ;break;
                    }
                    case 2: {
                        UserProfile userProfile=new UserProfile(TUP.getUseremail(),TUP.getUserpassword(),TUP.getUserprofilepic(),TUP.getUserstatus(),TUP.getUsername(),TUP.getUseravatarpic(),TUP.getUseravatarname(),lat,lng,materials,equipments);
                        startActivity(new Intent(_content, Social.class).putExtra("USERPROFILE3",userProfile));
                        mRef.setValue(userProfile);
                        break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                final String lat=String.valueOf(currentLocation.getLatitude());
                final String lng=String.valueOf(currentLocation.getLongitude());
                materials=TUP_NOW.getUsermaterials();
                equipments=TUP_NOW.getUserequipments();
                switch (tab.getPosition()) {
                    //after each select it updates the position to the database
                    case 0: {
                        UserProfile userProfile=new UserProfile(TUP.getUseremail(),TUP.getUserpassword(),TUP.getUserprofilepic(),TUP.getUserstatus(),TUP.getUsername(),TUP.getUseravatarpic(),TUP.getUseravatarname(),lat,lng,materials,equipments);
                        startActivity(new Intent(_content,Inventory.class).putExtra("USERPROFILE3",TUP_NOW));
                        mRef.setValue(userProfile);
                        break;
                    }
                    case 1: {
                        UserProfile userProfile=new UserProfile(TUP.getUseremail(),TUP.getUserpassword(),TUP.getUserprofilepic(),TUP.getUserstatus(),TUP.getUsername(),TUP.getUseravatarpic(),TUP.getUseravatarname(),lat,lng,materials,equipments);
                        startActivity(new Intent(_content, Skill.class).putExtra("USERPROFILE3",TUP_NOW));
                        mRef.setValue(userProfile);
                        break;
                    }
                    case 2: {
                        UserProfile userProfile=new UserProfile(TUP.getUseremail(),TUP.getUserpassword(),TUP.getUserprofilepic(),TUP.getUserstatus(),TUP.getUsername(),TUP.getUseravatarpic(),TUP.getUseravatarname(),lat,lng,materials,equipments);
                        startActivity(new Intent(_content, Social.class).putExtra("USERPROFILE3",TUP_NOW));
                        mRef.setValue(userProfile);
                        break;
                    }
                }


            }
        });

        mode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (counter%3){
                    case 1:{
                        mRef.setValue(TUP);
                        mode_button.setImageResource(R.drawable.fightmode);
                        mode_button.setScaleType(ImageView.ScaleType.CENTER);
                        mode_button.setBackground(getDrawable(R.drawable.button_shape_round_one));
                        counter++;
                        break;}
                    case 2 :{
                        mRef.setValue(TUP);
                        mode_button.setImageResource(R.drawable.buildmode);
                        mode_button.setScaleType(ImageView.ScaleType.CENTER);
                        mode_button.setBackground(getDrawable(R.drawable.button_shape_round_one));
                        counter++;
                        //ToDo Ground overlay done need to do multiple -> not allowed (tried the array walkthrough and all )
                            LatLng northeast=new LatLng(currentLocation.getLatitude()+0.001,currentLocation.getLongitude()+0.001);
                            LatLng southwest=new LatLng(currentLocation.getLatitude()-0.001,currentLocation.getLongitude()-0.001);
                            LatLngBounds bounds=new LatLngBounds(southwest,northeast);

                            BitmapDescriptor image=BitmapDescriptorFactory.fromResource(R.drawable.grid);

                            GroundOverlayOptions options=new GroundOverlayOptions().image(image).positionFromBounds(bounds).transparency(0f);

                            mMap.addGroundOverlay(options).setClickable(true);
                            mMap.addGroundOverlay(options).setTransparency(0f);
                            mMap.setOnGroundOverlayClickListener(new GoogleMap.OnGroundOverlayClickListener() {
                                @Override
                                public void onGroundOverlayClick(GroundOverlay groundOverlay) {
                                    Toast.makeText(Mainmenu.this, "player clicked ",Toast.LENGTH_SHORT ).show(); }
                            });
                        break;}
                    case 0:{
                        mRef.setValue(TUP);
                        mode_button.setImageResource(R.drawable.collectmode);
                        mode_button.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        mode_button.setBackground(getDrawable(R.drawable.button_shape_round));
                        counter++;


                        LatLng spawn=collectorScreen.spawn(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                        //icons has to be png not xml
                        mMap.addMarker(new MarkerOptions().position(spawn).title("Bomb").icon(BitmapDescriptorFactory.fromResource(R.drawable.bomb)));

                        LatLng spawn_1=collectorScreen.spawn(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(spawn_1).title("Leaf").icon(BitmapDescriptorFactory.fromResource(R.drawable.leaf)));

                        LatLng spawn_2=collectorScreen.spawn(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(spawn_2).title("Animal Resource").icon(BitmapDescriptorFactory.fromResource(R.drawable.animal)));

                        LatLng spawn_3=collectorScreen.spawn(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                        mMap.addMarker(new MarkerOptions().position(spawn_3).title("Iron").icon(BitmapDescriptorFactory.fromResource(R.drawable.iron)));

                        break;}
                }


            }
        });

    }

    private void moveCamera(LatLng latLng, float ZOOM) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(TUP.getUsername()).icon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker)));
        mMap.addCircle(new CircleOptions().center(latLng).radius(1000f).strokeWidth(1f).strokeColor(Color.BLUE).fillColor(Color.argb(0.2f,0f, 142f,255f)));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));
    }
    private void moveCameraOther(LatLng latLng, float ZOOM) {
        if (TUP_OTHER!=null) {
            mMap.addMarker(new MarkerOptions().position(latLng).title(TUP_OTHER.getUsername()).icon(BitmapDescriptorFactory.fromResource(R.drawable.enemy_marker)));
            mMap.addCircle(new CircleOptions().center(latLng).radius(1000f).strokeWidth(1f).strokeColor(Color.RED).fillColor(Color.parseColor("#33FF0026")));//the Least you go the less you get AARRGGBB
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));
        }
    }


    private void getCurrentLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (_permissionGranted) {
                final Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            currentLocation = (Location) task.getResult();
                            LatLng CURRENT=new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            moveCamera(CURRENT, DEFAULT_ZOOM);

                            if(TUP_OTHER!=null){
                                Double OTHER_LOCATION_LAT=Double.valueOf(TUP_OTHER.getUserlat());
                                Double OTHER_LOCATION_LNG=Double.valueOf(TUP_OTHER.getUserlng());
                                LatLng OTHER_LOCATION=new LatLng(OTHER_LOCATION_LAT,OTHER_LOCATION_LNG);
                                moveCameraOther(OTHER_LOCATION, DEFAULT_ZOOM);

                                mMap.addPolyline(new PolylineOptions().add(CURRENT,OTHER_LOCATION).color(Color.YELLOW).width(2f));

                            }
                            generateCollectables();
                                }


                        else {
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

    public void generateCollectables(){
        LatLng spawn=collectorScreen.spawn(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        //icons has to be png not xml
        mMap.addMarker(new MarkerOptions().position(spawn).title("Bomb").icon(BitmapDescriptorFactory.fromResource(R.drawable.bomb)));

        LatLng spawn_1=collectorScreen.spawn(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        mMap.addMarker(new MarkerOptions().position(spawn_1).title("Leaf").icon(BitmapDescriptorFactory.fromResource(R.drawable.leaf)));

        LatLng spawn_2=collectorScreen.spawn(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        mMap.addMarker(new MarkerOptions().position(spawn_2).title("Animal Resource").icon(BitmapDescriptorFactory.fromResource(R.drawable.animal)));

        LatLng spawn_3=collectorScreen.spawn(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        mMap.addMarker(new MarkerOptions().position(spawn_3).title("Iron").icon(BitmapDescriptorFactory.fromResource(R.drawable.iron)));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                switch (marker.getTitle()) {
                    case "Bomb": {
                        TUP_NOW.addMaterials("Bomb ");
                        marker.remove();
                        break;
                    }
                    case "Leaf": {
                        TUP_NOW.addMaterials("Leaf ");
                        marker.remove();
                        break;
                    }
                    case "Animal Resource": {
                        TUP_NOW.addMaterials("AnimalResource ");
                        marker.remove();
                        break;
                    }
                    case "Iron": {
                        TUP_NOW.addMaterials("Iron ");
                        marker.remove();
                        break;
                    }
                }
                return false;

            }
        });

    }


}

