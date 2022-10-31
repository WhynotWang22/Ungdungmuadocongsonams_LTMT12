package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Cart {
    @SerializedName("_id")
    @Expose
    public String _id;
    @SerializedName("idUser")
    @Expose
    public String userId;
    @SerializedName("products")
    @Expose
    public ArrayList<Products> products;

    public Cart() {
    }

    public Cart(String _id, String userId, ArrayList<Products> products) {
        this._id = _id;
        this.userId = userId;
        this.products = products;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }
}
