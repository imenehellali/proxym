package com.example.hella.proxym.Util;



import android.os.Parcel;
import android.os.Parcelable;

import com.example.hella.proxym.Collectables;


import java.util.ArrayList;

public class UserProfile implements Parcelable{

    private static String DEFAULT="DEFAULT";
    private String useremail;
    private String userpassword;
    private String userstatus;

    private String username;
    private String useravatarname;

    private String useravatarpic;
    private String  userprofilepic;

    private String userlng;
    private String userlat;

    private ArrayList<Collectables> usermaterials;
    private ArrayList<Collectables> userequipments;

    private ArrayList<UserProfile> userfriends;

    public UserProfile(){
        useremail=DEFAULT;
        userpassword=DEFAULT;
        userprofilepic=DEFAULT;
        userstatus=DEFAULT;
        username=DEFAULT;
        useravatarname=DEFAULT;
        useravatarpic=DEFAULT;
        userlng=DEFAULT;
        userlat=DEFAULT;
    }

    public UserProfile(String useremail, String userpassword, String userprofilepic, String userstatus) {
        this.useremail=useremail;
        this.userpassword=userpassword;
        this.userprofilepic=userprofilepic;
        this.userstatus=userstatus;

        username=DEFAULT;
        useravatarname=DEFAULT;
        useravatarpic=DEFAULT;
        userlng=DEFAULT;
        userlat=DEFAULT;
    }

    public UserProfile(String useremail, String userpassword, String userprofilepic, String userstatus, String username, String useravatarpic, String useravatarname){
        this.useremail=useremail;
        this.userpassword=userpassword;
        this.userprofilepic=userprofilepic;
        this.userstatus=userstatus;
        this.username=username;
        this.useravatarpic=useravatarpic;
        this.useravatarname=useravatarname;

        userlng=DEFAULT;
        userlat=DEFAULT;
    }

    public UserProfile(String useremail, String userpassword, String userprofilepic, String userstatus, String username, String useravatarpic, String useravatarname, String userlat, String userlng){
        this.useremail=useremail;
        this.userpassword=userpassword;
        this.userprofilepic=userprofilepic;
        this.userstatus=userstatus;
        this.username=username;
        this.useravatarpic=useravatarpic;
        this.useravatarname=useravatarname;
        this.userlat=userlat;
        this.userlng=userlng;
    }


    public String getUsername(){
        return username;
    }
    public String getUseravatarname(){
        return useravatarname;
    }
    public String getUseremail() {
        return useremail;
    }
    public String getUserpassword() {
        return userpassword;
    }
    public String getUserlng() {

        return userlng;
    }
    public String getUserlat() {
        return userlat;
    }
    public ArrayList<Collectables> getUsermaterials() {
        return usermaterials;
    }
    public ArrayList<Collectables> getUserequipments() {
        return userequipments;
    }
    public ArrayList<UserProfile> getUserfriends() {
        return userfriends;
    }
    public String getUseravatarpic() {

        return useravatarpic;
    }
    public String getUserprofilepic() {

        return userprofilepic;
    }
    public String getUserstatus() {

        return userstatus;
    }

    protected UserProfile(Parcel in) {
        useremail = in.readString();
        userpassword = in.readString();
        userstatus = in.readString();
        username = in.readString();
        useravatarname = in.readString();
        useravatarpic = in.readString();
        userprofilepic = in.readString();
        userlng = in.readString();
        userlat = in.readString();
        if (in.readByte() == 0x01) {
            usermaterials = new ArrayList<Collectables>();
            in.readList(usermaterials, Collectables.class.getClassLoader());
        } else {
            usermaterials = null;
        }
        if (in.readByte() == 0x01) {
            userequipments = new ArrayList<Collectables>();
            in.readList(userequipments, Collectables.class.getClassLoader());
        } else {
            userequipments = null;
        }
        if (in.readByte() == 0x01) {
            userfriends = new ArrayList<UserProfile>();
            in.readList(userfriends, UserProfile.class.getClassLoader());
        } else {
            userfriends = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(useremail);
        dest.writeString(userpassword);
        dest.writeString(userstatus);
        dest.writeString(username);
        dest.writeString(useravatarname);
        dest.writeString(useravatarpic);
        dest.writeString(userprofilepic);
        dest.writeString(userlng);
        dest.writeString(userlat);
        if (usermaterials == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(usermaterials);
        }
        if (userequipments == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(userequipments);
        }
        if (userfriends == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(userfriends);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserProfile> CREATOR = new Parcelable.Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };
}
