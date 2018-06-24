package com.example.hella.proxym;


import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hella.proxym.Util.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Inventory extends AppCompatActivity {


    private TabLayout inventory_tabs;
    private LinearLayout fragmentEquipment, fragmentCrafting, fragmentBuilding, layout_material;
    private LinearLayout layout_animal_resource,layout_iron, layout_leaf,layout_bomb;
    private TextView bomb_number,leaf_number,iron_number,animal_resource_number ;

    private UserProfile TUP; //Plan B if anything crashes :p -> don't know why i could't update my database anymore :(


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mRef;



    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference(user.getUid());

        TUP=(UserProfile) Inventory.this.getIntent().getExtras().getParcelable("USERPROFILE3");
        Toast.makeText(this, TUP.getUsermaterials().get(0), Toast.LENGTH_LONG).show();

        layout_animal_resource=(LinearLayout) findViewById(R.id.layout_animal_resource);
        layout_iron=(LinearLayout) findViewById(R.id.layout_iron);
        layout_leaf=(LinearLayout) findViewById(R.id.layout_leaf);
        layout_bomb=(LinearLayout) findViewById(R.id.layout_bomb);

        bomb_number=(TextView) findViewById(R.id.bomb_number);
        leaf_number=(TextView) findViewById(R.id.leaf_number);
        iron_number=(TextView) findViewById(R.id.iron_number);
        animal_resource_number=(TextView) findViewById(R.id.animal_resource_number);


        inventory_tabs = (TabLayout) findViewById(R.id.inventory_tabs);

        inventory_tabs.addTab(inventory_tabs.newTab(),0);
        inventory_tabs.addTab(inventory_tabs.newTab(),1);
        inventory_tabs.addTab(inventory_tabs.newTab(),2);
        inventory_tabs.addTab(inventory_tabs.newTab(),3);

        inventory_tabs.getTabAt(0).setIcon(R.drawable.equipment);
        inventory_tabs.getTabAt(1).setIcon(R.drawable.material);
        inventory_tabs.getTabAt(2).setIcon(R.drawable.crafting);
        inventory_tabs.getTabAt(3).setIcon(R.drawable.building);

        fragmentEquipment=(LinearLayout)findViewById(R.id.fragmentEquipment);
        layout_material=(LinearLayout)findViewById(R.id.layout_material);
        fragmentCrafting=(LinearLayout) findViewById(R.id.fragmentCrafting);
        fragmentBuilding=(LinearLayout) findViewById(R.id.fragmentBuilding);

        //updating the material layout
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                //find problem and then resolve
                ArrayList<String> materials = TUP.getUsermaterials();
                if (!materials.isEmpty()) {
                    for (Iterator<String> it = materials.iterator(); it.hasNext(); ) {
                        String material = it.next();
                        String material1 = material.substring(0, material.indexOf(' ')).trim();
                        String number = material.substring(material.indexOf(' '));
                        switch (material1) {
                            case "Bomb": {
                                layout_bomb.setVisibility(View.VISIBLE);
                                bomb_number.setText(number);
                                Toast.makeText(Inventory.this, material, Toast.LENGTH_LONG).show();
                                break;
                            }
                            case "Iron": {
                                layout_iron.setVisibility(View.VISIBLE);
                                iron_number.setText(number);
                                break;
                            }
                            case "Leaf": {
                                layout_leaf.setVisibility(View.VISIBLE);
                                leaf_number.setText(number);
                                break;
                            }
                            case "AnimalResource": {
                                layout_animal_resource.setVisibility(View.VISIBLE);
                                animal_resource_number.setText(number);
                                break;
                            }
                        }
                    }
                }

                else{
                    try {
                        layout_bomb.setVisibility(View.VISIBLE);
                        bomb_number.setText("0");
                        layout_iron.setVisibility(View.VISIBLE);
                        iron_number.setText("0");
                        layout_leaf.setVisibility(View.VISIBLE);
                        leaf_number.setText("0");
                        layout_animal_resource.setVisibility(View.VISIBLE);
                        animal_resource_number.setText("0");
                    } catch (Exception e) {
                        Toast.makeText(Inventory.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Inventory.this, "DATABASE PROBLEM", Toast.LENGTH_SHORT).show();
            }
        });

        //updating the equipment layout
       /* mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                ArrayList<String> equipments = userProfile.getUserequipments();
                if (!equipments.isEmpty()) {
                    for (Iterator<String> it = equipments.iterator(); it.hasNext(); ) {
                        String equipment = it.next();
                        String equipment1 = equipment.substring(0, equipment.indexOf(' ')).trim();
                        String number = equipment.substring(equipment.indexOf(' '));
                        switch (equipment1) {
                            //ToDo need to add some Equipments
                        }
                    }
                }

                else{
                    //ToDo add the DEFAULT 0 equipment
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Inventory.this, "DATABASE PROBLEM", Toast.LENGTH_SHORT).show();
            }
        });*/


        inventory_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:{
                        fragmentEquipment.setVisibility(View.VISIBLE);
                        layout_material.setVisibility(View.GONE);
                        fragmentBuilding.setVisibility(View.GONE);
                        fragmentCrafting.setVisibility(View.GONE);
                        break;

                    }
                    case 1:{
                        layout_material.setVisibility(View.VISIBLE);
                        fragmentCrafting.setVisibility(View.GONE);
                        fragmentBuilding.setVisibility(View.GONE);
                        fragmentEquipment.setVisibility(View.GONE);
                        break;

                    }
                    case 2:{
                        fragmentCrafting.setVisibility(View.VISIBLE);
                        fragmentEquipment.setVisibility(View.GONE);
                        layout_material.setVisibility(View.GONE);
                        fragmentBuilding.setVisibility(View.GONE);
                        break;

                    }
                    case 3:{
                        fragmentBuilding.setVisibility(View.VISIBLE);
                        fragmentCrafting.setVisibility(View.GONE);
                        fragmentEquipment.setVisibility(View.GONE);
                        layout_material.setVisibility(View.GONE);
                        break;

                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:{
                        fragmentEquipment.setVisibility(View.VISIBLE);
                        layout_material.setVisibility(View.GONE);
                        fragmentBuilding.setVisibility(View.GONE);
                        fragmentCrafting.setVisibility(View.GONE);
                        break;

                    }
                    case 1:{
                        layout_material.setVisibility(View.VISIBLE);
                        fragmentCrafting.setVisibility(View.GONE);
                        fragmentBuilding.setVisibility(View.GONE);
                        fragmentEquipment.setVisibility(View.GONE);
                        break;

                    }
                    case 2:{
                        fragmentCrafting.setVisibility(View.VISIBLE);
                        fragmentEquipment.setVisibility(View.GONE);
                        layout_material.setVisibility(View.GONE);
                        fragmentBuilding.setVisibility(View.GONE);
                        break;

                    }
                    case 3:{
                        fragmentBuilding.setVisibility(View.VISIBLE);
                        fragmentCrafting.setVisibility(View.GONE);
                        fragmentEquipment.setVisibility(View.GONE);
                        layout_material.setVisibility(View.GONE);
                        break;

                    }
                }

            }
        });

    }
}
