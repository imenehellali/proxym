package com.example.hella.proxym.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hella.proxym.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class PopupScreenAdapter implements GoogleMap.InfoWindowAdapter {

   private final View mWindow;
   private Context mContext;

   public PopupScreenAdapter(Context context){
       mContext=context;
       mWindow= LayoutInflater.from(context).inflate(R.layout.popupscreen,null);
   }

    @Override
    public View getInfoWindow(Marker marker) {
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return mWindow;
    }
}
