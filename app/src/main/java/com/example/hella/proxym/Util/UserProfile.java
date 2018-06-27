package com.example.hella.proxym.Util;



import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


import java.util.ArrayList;
import java.util.Iterator;
public class UserProfile implements Parcelable {

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

    private ArrayList<String> usermaterials;
    private ArrayList<String> userequipments;
    private ArrayList<String> usereaskedresources;

    private ArrayList<UserProfile> userfriends;


    public void addMaterials(String element) {
        int i=0;
        if(!usermaterials.isEmpty()&& usermaterials!=null){
            for (Iterator<String> it = usermaterials.iterator(); it.hasNext(); ) {
                String material = it.next();
                String material1=material.substring(0,material.indexOf(' ')).trim();
                if(material1.equals(element.trim())){
                    String output=material.substring(material.indexOf(' ')).trim();
                    int count=(int)Integer.parseInt(output);
                    material=element+" "+(++count);
                    Log.d("MATERIAL", " "+i+" "+ material);
                    return;
                }//found it in array and added it -> so quit function
                i++;
            }
            element+="1";
            usermaterials.add(i, element);
            Log.d("MATERIAL", " "+i+" "+ element);
            return;
        }// didn't find it in array so add new one
        else{
            usermaterials=new ArrayList<String>();
            element+="1";
            usermaterials.add(i, element);
            Log.d("MATERIAL", " "+i+" "+ element);
            return;
        }//first element

    }

    public void setUserlng(String userlng) {
        this.userlng = userlng;
    }

    public void setUserlat(String userlat) {
        this.userlat = userlat;
    }

    public void addEquipments(String element) {
        int i=0;
        if(!userequipments.isEmpty()&& userequipments!=null){
            for (Iterator<String> it = userequipments.iterator(); it.hasNext(); ) {
                String equipment = it.next();
                String equipment1= equipment.substring(0, equipment.indexOf(' ')).trim();
                if(equipment1.equals(element.trim())){
                    String output= equipment.substring(equipment.indexOf(' ')).trim();
                    int count=(int)Integer.parseInt(output);
                    equipment =element+" "+(++count);
                    Log.d("EQUIPMENT", " "+i+" "+ equipment);
                    return;
                }//found it in array and added it -> so quit function
                i++;
            }
            element+="1";
            userequipments.add(i, element);
            Log.d("EQUIPMENT", " "+i+" "+ element);
            return;
        }// didn't find it in array so add new one
        else{
            userequipments=new ArrayList<String>();
            element+="1";
            userequipments.add(i, element);
            Log.d("EQUIPMENT", " "+i+" "+ element);
            return;
        }//first element

    }

    public ArrayList<String> getUsereaskedresources() {
        return usereaskedresources;
    }

    public void addAskedResources(String element, String otherUserUID) {
        if(usereaskedresources==null){
            usereaskedresources=new ArrayList<String>();
            String toAdd=element+" "+otherUserUID;
            usereaskedresources.add(toAdd);
            Log.d("ASK FOR RESOURCE ", toAdd);
        }
        else{
            String toAdd=element+" "+otherUserUID;
            usereaskedresources.add(toAdd);
            Log.d("ASK FOR RESOURCE ", toAdd);
        }

    }

    public void setUsermaterials(ArrayList<String> usermaterials) {
        this.usermaterials = usermaterials;
    }

    public void setUserequipments(ArrayList<String> userequipments) {
        this.userequipments = userequipments;
    }

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
        usermaterials=new ArrayList<String>();
        userequipments=new ArrayList<String>();
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
        usermaterials=new ArrayList<String>();
        userequipments=new ArrayList<String>();
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
        usermaterials=new ArrayList<String>();
        userequipments=new ArrayList<String>();
    }



    public UserProfile(String useremail, String userpassword, String userprofilepic, String userstatus, String username, String useravatarpic, String useravatarname, String userlat, String userlng, ArrayList<String> usermaterials, ArrayList<String> userequipments){
        this.useremail=useremail;
        this.userpassword=userpassword;
        this.userprofilepic=userprofilepic;
        this.userstatus=userstatus;
        this.username=username;
        this.useravatarpic=useravatarpic;
        this.useravatarname=useravatarname;
        this.userlat=userlat;
        this.userlng=userlng;
        this.usermaterials=usermaterials;
        this.userequipments=userequipments;
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
    public ArrayList<String> getUsermaterials() {
        return usermaterials;
    }
    public ArrayList<String> getUserequipments() {
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
                usermaterials = new ArrayList<String>();
                in.readList(usermaterials, String.class.getClassLoader());
            } else {
                usermaterials = null;
            }
            if (in.readByte() == 0x01) {
                userequipments = new ArrayList<String>();
                in.readList(userequipments, String.class.getClassLoader());
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
