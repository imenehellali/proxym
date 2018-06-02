package com.example.hella.proxym;

import android.view.View;

public class ID {
    private int id;
    private View view;

    public ID(View view, int id) {
        this.id = id;
        this.view=view;
    }
    public int getId(){
        return this.id;
    }

    public View getView() {
        return view;
    }
}
