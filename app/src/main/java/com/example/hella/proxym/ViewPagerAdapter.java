package com.example.hella.proxym;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> fragmentTitle=new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return fragmentTitle.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position){
        return fragmentTitle.get(position);
    }

    public void AddFragment(Fragment fragment, String Title){
        fragmentList.add(fragment);
        fragmentTitle.add(Title);
    }
}
