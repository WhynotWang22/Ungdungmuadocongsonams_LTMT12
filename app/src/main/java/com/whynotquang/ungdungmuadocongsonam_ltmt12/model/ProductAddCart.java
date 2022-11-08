package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductAddCart {
    @SerializedName("_id")
    private String _id;
    @SerializedName("userId")
    private String userId;
    @SerializedName("products")
    private List<Products> products;
    @SerializedName("Total")
    private int Total;
    @SerializedName("createdAt")
    private String createdAt;
    @SerializedName("updatedAt")
    private String updatedAt;
    @SerializedName("__v")
    private int __v;

    public String get_id() {
        return _id;
    }

    public String getUserId() {
        return userId;
    }

    public List<Products> getProducts() {
        return products;
    }

    public int getTotal() {
        return Total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int get__v() {
        return __v;
    }
}
