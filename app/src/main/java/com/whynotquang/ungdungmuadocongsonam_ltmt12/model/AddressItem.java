package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressItem {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("address")
    @Expose
    private List<Address> address = null;
    @SerializedName("_id")
    @Expose
    private String id;

    public AddressItem() {
    }

    public AddressItem(String userId, List<Address> address, String id) {
        this.userId = userId;
        this.address = address;
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
