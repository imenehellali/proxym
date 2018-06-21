package com.example.hella.proxym;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class Social extends AppCompatActivity {
    private TabLayout social_tabs;
    LinearLayout fragmentProfile,fragmentUser,fragmentChat,fragmentQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social);

        social_tabs = (TabLayout) findViewById(R.id.social_tabs);

        social_tabs.addTab(social_tabs.newTab(),0);
        social_tabs.addTab(social_tabs.newTab(),1);
        social_tabs.addTab(social_tabs.newTab(),2);
        social_tabs.addTab(social_tabs.newTab(),3);


        social_tabs.getTabAt(0).setIcon(R.drawable.profile);
        social_tabs.getTabAt(1).setIcon(R.drawable.user);
        social_tabs.getTabAt(2).setIcon(R.drawable.chat_double);
        social_tabs.getTabAt(3).setIcon(R.drawable.quest);


        fragmentProfile=(LinearLayout) findViewById(R.id.fragmentProfile);
        fragmentUser=(LinearLayout)findViewById(R.id.fragmentUser);
        fragmentChat=(LinearLayout)findViewById(R.id.fragmentChat);
        fragmentQuest=(LinearLayout)findViewById(R.id.fragmentQuest);

       social_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               switch (tab.getPosition()){
                   case 0:{
                       fragmentProfile.setVisibility(View.VISIBLE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
                   case 1:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.VISIBLE);
                       break;

                   }
                   case 2:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.VISIBLE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
                   case 3:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.VISIBLE);
                       fragmentUser.setVisibility(View.GONE);
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
                       fragmentProfile.setVisibility(View.VISIBLE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
                   case 1:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.VISIBLE);
                       break;

                   }
                   case 2:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.VISIBLE);
                       fragmentQuest.setVisibility(View.GONE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
                   case 3:{
                       fragmentProfile.setVisibility(View.GONE);
                       fragmentChat.setVisibility(View.GONE);
                       fragmentQuest.setVisibility(View.VISIBLE);
                       fragmentUser.setVisibility(View.GONE);
                       break;

                   }
               }

           }
       });

    }
}
