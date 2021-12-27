package com.example.handsonfirebase;

class loginhelperclass {

    String userName, password, userEmail, phoneNo;

    public loginhelperclass() {
//        Empty constructor required
    }

    public loginhelperclass(String phoneNo, String userName, String password, String userEmail) {
        this.phoneNo = phoneNo;
        this.userName = userName;
        this.password = password;
        this.userEmail = userEmail;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserEmail() {
        return userEmail;
    }
}