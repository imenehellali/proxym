package com.example.hella.proxym.Util;



public class UserProfile {

    private String useremail;
    private String userpassword;

    private String username;
    private String useravatar;

    public UserProfile(String useremail, String userpassword) {
        this.useremail = useremail;
        this.userpassword = userpassword;
    }

    public UserProfile() {
    }

    public void setUsername(String username){
        this.username=username;
    }
    public String getUsername(){
        return username;
    }
    public void setUseravatar(String useravatar){
        this.useravatar=useravatar;
    }
    public String getUseravatar(){
        return useravatar;
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
}
