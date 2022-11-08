package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import com.google.gson.annotations.SerializedName;

public class Products {
    public String productId;
    public String color;
    public String size;
    public int quantity;
    public int amount;
    public String _id;
    @SerializedName("title")
    public String title;
    @SerializedName("ProductIMG")
    public String ProductIMG;
    @SerializedName("price")
    public int price;

    public String getProductId() {
        return productId;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getAmount() {
        return amount;
    }

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getProductIMG() {
        return ProductIMG;
    }

    public int getPrice() {
        return price;
    }
}
