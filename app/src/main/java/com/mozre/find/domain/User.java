package com.mozre.find.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class User {

    private String userName;
    private String password;
    private String mail;
    private String phone;
    private String userIconAddress;
    private String token;
    private static SharedPreferences sharedPreferences;

    public User() {
    }

    public User(Context context) {
        sharedPreferences = context.getSharedPreferences("userdata", Context.MODE_PRIVATE);
    }

    public User(String userName, String password, String mail, String phone, String userIconAddress) {
        this.userName = userName;
        this.password = password;
        this.mail = mail;
        this.phone = phone;
        this.userIconAddress = userIconAddress;
    }

    public String getUserName() {
        if (TextUtils.isEmpty(userName)) {
            userName = sharedPreferences.getString("username", null);
        }
        return userName;
    }

    public void setUserName(String userName) {
        if (sharedPreferences.edit().putString("username", userName).commit()) {
            this.userName = userName;
        }
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        if (TextUtils.isEmpty(mail)) {
            this.mail = sharedPreferences.getString("mail", null);
        }
        return mail;
    }

    public void setMail(String mail) {
        if (sharedPreferences.edit().putString("mail", mail).commit()) {
            this.mail = mail;
        }
    }

    public String getPhone() {
        if (TextUtils.isEmpty(phone)) {
            this.phone = sharedPreferences.getString("phone", null);
        }
        return phone;
    }

    public void setPhone(String phone) {
        if (sharedPreferences.edit().putString("phone", phone).commit()) {
            this.phone = phone;
        }
    }

    public void setUserIconAddress(String userIconAddress) {
        if (sharedPreferences.edit().putString("iconaddress", userIconAddress).commit()) {
            this.userIconAddress = userIconAddress;
        }
    }

    public String getPassword() {
        return password;
    }

    public String getUserIconAddress() {
        if (TextUtils.isEmpty(userIconAddress)) {
            this.userIconAddress = sharedPreferences.getString("iconaddress", null);

        }
        return userIconAddress;
    }

    public String getToken() {
        if (this.token == null) {
            this.token = sharedPreferences.getString("token", null);
        }
        return token;
    }

    public void setToken(String token) {
        if (sharedPreferences.edit().putString("token", token).commit()) {
            this.token = token;
        }
    }
}
