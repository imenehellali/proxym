package com.example.hella.proxym;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
    private ViewPager inventory_extended;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    ArrayList<Collectables> collectable_array;

    private LinearLayout layout_animal_resource,layout_iron, layout_leaf,layout_bomb;
    private TextView bomb_number,leaf_number,iron_number,animal_resource_number ;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        inventory_tabs = (TabLayout) findViewById(R.id.inventory_tabs);
        inventory_extended = (ViewPager) findViewById(R.id.inventory_extended);

        ViewPagerAdapter _adapter = new ViewPagerAdapter(getSupportFragmentManager());


        _adapter.AddFragment(new FragmentEquipment(), "Equipment");
        _adapter.AddFragment(new FragmentMaterial(), "Material");
        _adapter.AddFragment(new FragmentCrafting(), "Crafting");
        _adapter.AddFragment(new FragmentBuilding(), "Building");

        inventory_extended.setAdapter(_adapter);
        inventory_tabs.setupWithViewPager(inventory_extended);

        inventory_tabs.getTabAt(0).setIcon(R.drawable.equipment);
        inventory_tabs.getTabAt(1).setIcon(R.drawable.material);
        inventory_tabs.getTabAt(2).setIcon(R.drawable.crafting);
        inventory_tabs.getTabAt(3).setIcon(R.drawable.building);

        layout_animal_resource=(LinearLayout) findViewById(R.id.layout_animal_resource);
        layout_iron=(LinearLayout) findViewById(R.id.layout_iron);
        layout_leaf=(LinearLayout) findViewById(R.id.layout_leaf);
        layout_bomb=(LinearLayout) findViewById(R.id.layout_bomb);

        bomb_number=(TextView) findViewById(R.id.bomb_number);
        leaf_number=(TextView) findViewById(R.id.leaf_number);
        iron_number=(TextView) findViewById(R.id.iron_number);
        animal_resource_number=(TextView) findViewById(R.id.animal_resource_number);

        collectable_array= Mainmenu.collectorScreen.collected;


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
            //ToDo ERROR : null object cannot access the frgmens from here
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
