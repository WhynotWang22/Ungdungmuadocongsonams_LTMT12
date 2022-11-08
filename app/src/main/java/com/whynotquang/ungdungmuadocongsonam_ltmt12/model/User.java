package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone_number")
    @Expose
    private Integer phoneNumber;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("tokens")
    @Expose
    private List<Object> tokens = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public String getAvatar() {
        return avatar;
    }

    public List<Object> getTokens() {
        return tokens;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Integer getV() {
        return v;
    }
}

