package com.example.hella.proxym;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentProfile extends Fragment {
    View _view;

    public FragmentProfile() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        _view=inflater.inflate(R.layout.profile_fragment,container,false);
        return _view;
    }
}
