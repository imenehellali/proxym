package com.example.hella.proxym;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class BuilderScreen implements Parcelable {
    private GoogleMap mMap;


    public BuilderScreen(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    protected BuilderScreen(Parcel in) {
        mMap = (GoogleMap) in.readValue(GoogleMap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(mMap);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BuilderScreen> CREATOR = new Parcelable.Creator<BuilderScreen>() {
        @Override
        public BuilderScreen createFromParcel(Parcel in) {
            return new BuilderScreen(in);
        }

        @Override
        public BuilderScreen[] newArray(int size) {
            return new BuilderScreen[size];
        }
    };
}