package com.example.handsonfirebase;


import android.net.Uri;

class loginhelperclass {

    String userName, userEmail, phoneNo, imageUrl;

    public loginhelperclass() {
//        Empty constructor required
    }

    public loginhelperclass(String phoneNo, String userName, String userEmail) {
        this.phoneNo = phoneNo;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public loginhelperclass(String phoneNo, String userName, String userEmail, String imageUrl) {
        this.phoneNo = phoneNo;
        this.userName = userName;
        this.userEmail = userEmail;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }
}