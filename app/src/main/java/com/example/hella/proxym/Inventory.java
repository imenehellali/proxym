package com.example.hella.proxym;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Iterator;

public class Inventory extends AppCompatActivity {


    private TabLayout inventory_tabs;
    private LinearLayout fragmentEquipment, fragmentCrafting, fragmentBuilding, layout_material;
    private LinearLayout layout_animal_resource,layout_iron, layout_leaf,layout_bomb;
    private TextView bomb_number,leaf_number,iron_number,animal_resource_number ;

    private ArrayList<Collectables> collectable_array=new ArrayList<Collectables>();


    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

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

        //ToDo save the collectable in database for each user than extract from it
        if (!collectable_array.isEmpty()) {
            for (Iterator<Collectables> it = collectable_array.iterator(); it.hasNext(); ) {
                Collectables collectables = it.next();
                switch (collectables.id){
                    case "Bomb":{
                        layout_bomb.setVisibility(View.VISIBLE);
                        bomb_number.setText(""+collectables.number);
                        Toast.makeText(this,"added "+collectables.number+ " "+collectables.id+" \n"+layout_bomb.getVisibility(), Toast.LENGTH_SHORT).show();
                    }
                    case "Iron":{
                        layout_iron.setVisibility(View.VISIBLE);
                        iron_number.setText(""+collectables.number);
                    }
                    case "Leaf":{
                        layout_leaf.setVisibility(View.VISIBLE);
                        leaf_number.setText(""+collectables.number);
                    }
                    case "Animal Resource":{
                        layout_animal_resource.setVisibility(View.VISIBLE);
                        animal_resource_number.setText(""+collectables.number);
                    }
                }
            }
        }else{
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
                Toast.makeText(Inventory.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }


    }
}
