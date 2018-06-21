package com.example.hella.proxym.Util;


import android.net.Uri;

import com.example.hella.proxym.Collectables;


import java.util.ArrayList;

public class UserProfile {

    private String useremail;
    private String userpassword;

    private String username;
    private String useravatarname;

    private String useravatarpic;
    private String  userprofilepic;

    private Double userlng;
    private Double userlat;

    private ArrayList<Collectables> usermaterials;
    private ArrayList<Collectables> userequipments;

    private ArrayList<UserProfile> userfriends;

    public UserProfile() {
    }

    public void setUsername(String username){
        this.username=username;
    }

    public String getUsername(){
        return username;
    }

    public void setUseravatarname(String useravatarname){
        this.useravatarname = useravatarname;
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

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public void setUserlng(Double userlng) {
        this.userlng = userlng;
    }

    public void setUserlat(Double userlat) {
        this.userlat = userlat;
    }

    public void setUsermaterials(ArrayList<Collectables> usermaterials) {
        this.usermaterials = usermaterials;
    }

    public void setUserequipments(ArrayList<Collectables> userequipments) {
        this.userequipments = userequipments;
    }

    public void setUserfriends(ArrayList<UserProfile> userfriends) {
        this.userfriends = userfriends;
    }

    public Double getUserlng() {

        return userlng;
    }

    public Double getUserlat() {
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


    public void setUseravatarpic(String useravatarpic) {
        this.useravatarpic = useravatarpic;
    }

    public String getUseravatarpic() {

        return useravatarpic;
    }

    public void setUserprofilepic(String userprofilepic) {
        this.userprofilepic = userprofilepic;
    }

    public String getUserprofilepic() {

        return userprofilepic;
    }
}
