package com.example.hella.proxym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;

public class Inventory extends AppCompatActivity {


    private TabLayout inventory_tabs;
    private ViewPager inventory_extended;
    private CollectorScreen collectorScreen;

    private LinearLayout layout_material_hori,layout_material;
    private ImageView material_image;
    private TextView material_number, material_description;

    //toDo constructor does not work -> ERROR
    public Inventory(CollectorScreen collectorScreen) {
        this.collectorScreen=collectorScreen;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.inventory);


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

        layout_material=(LinearLayout) findViewById(R.id.layout_material);
        layout_material_hori=(LinearLayout) findViewById(R.id.layout_material_hori);
        material_image=(ImageView) findViewById(R.id.material_image);
        material_number=(TextView) findViewById(R.id.material_number);
        material_description=(TextView) findViewById(R.id.material_description);

        //ToDo it works but i think because of the new doesn't deliver
        for (Iterator<Collectables> it = collectorScreen.collected.iterator(); it.hasNext(); ) {
            Collectables collectables = it.next();

            material_image.setImageResource(collectables.image);
            material_number.setText(""+collectables.number);
            material_description.setText(""+collectables.id);
/*
            layout_material_hori.addView(material_image,0);
            layout_material_hori.addView(material_number,1);
            layout_material_hori.addView(material_description,2);
*/
            LinearLayout copy=new LinearLayout(this);
            copy.setLayoutParams(layout_material_hori.getLayoutParams());
            copy.addView(material_image,1);
            copy.addView(material_number,2);
            copy.addView(material_description,3);

            layout_material.addView(copy);
        }
    }
}
