package com.example.handsonfirebase;

import android.net.Uri;

class loginhelperclass {

    Uri imageUri;

    String userName, userEmail, phoneNo;

    public loginhelperclass() {
//        Empty constructor required
    }

    public loginhelperclass(String phoneNo, String userName, String userEmail) {
        this.phoneNo = phoneNo;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
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