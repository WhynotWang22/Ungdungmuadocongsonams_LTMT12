package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("DetailAddress")
    @Expose
    private String detailAddress;
    @SerializedName("NumberPhone")
    @Expose
    private Integer numberPhone;
    @SerializedName("_id")
    @Expose
    private String id;

    public Address() {
    }

    public Address(String name, String detailAddress, Integer numberPhone, String id) {
        this.name = name;
        this.detailAddress = detailAddress;
        this.numberPhone = numberPhone;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Integer getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(Integer numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
