package com.example.hella.proxym;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Avatarscreen extends AppCompatActivity {

    private ViewPager view_pager;
    private SlideAdapter _adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_screen);

        view_pager=(ViewPager) findViewById(R.id.view_pager);
        _adapter=new SlideAdapter(this);
        view_pager.setAdapter(_adapter);
    }
}
