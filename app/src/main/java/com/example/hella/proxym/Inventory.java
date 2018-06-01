package com.example.hella.proxym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class Inventory extends AppCompatActivity {

    private TabLayout inventory_tabs;
    private ViewPager inventory_extended;

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

    }
}
