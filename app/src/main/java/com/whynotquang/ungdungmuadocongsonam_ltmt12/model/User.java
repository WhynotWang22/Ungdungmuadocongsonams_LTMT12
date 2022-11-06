package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

public class User {
    public String _id;
    public String email;
    public String password;
    public String full_name;
    public int phone_number;
    public String address;
    public String avatar;
    public String token;

    public User() {
    }

    public User(String _id, String email, String password, String full_name, int phone_number, String address, String avatar, String token) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.full_name = full_name;
        this.phone_number = phone_number;
        this.address = address;
        this.avatar = avatar;
        this.token = token;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
