package com.example.hella.proxym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class Social extends AppCompatActivity {
    private TabLayout social_tabs;
    private ViewPager social_extended;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social);

        social_tabs = (TabLayout) findViewById(R.id.social_tabs);
        social_extended = (ViewPager) findViewById(R.id.social_extended);

        ViewPagerAdapter _adapter = new ViewPagerAdapter(getSupportFragmentManager());

        _adapter.AddFragment(new FragmentProfile(), "Profile");
        _adapter.AddFragment(new FragmentUser(), "User");
        _adapter.AddFragment(new FragmentChat(), "Chat");
        _adapter.AddFragment(new FragmentQuest(), "Quest");

        social_extended.setAdapter(_adapter);
        social_tabs.setupWithViewPager(social_extended);

        social_tabs.getTabAt(0).setIcon(R.drawable.profile);
        social_tabs.getTabAt(1).setIcon(R.drawable.user);
        social_tabs.getTabAt(2).setIcon(R.drawable.chat);
        social_tabs.getTabAt(3).setIcon(R.drawable.quest);

    }
}
