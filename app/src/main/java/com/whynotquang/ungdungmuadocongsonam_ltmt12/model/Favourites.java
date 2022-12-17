package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

public class Favourites {
    public String _id;
    public String userId;
    public String productId;
    public boolean isFavorite;
    public String title;
    public String ProductIMG;
    public int price;
    public int sold;

    public String get_id() {
        return _id;
    }

    public String getUserId() {
        return userId;
    }

    public String getProductId() {
        return productId;
    }

    public boolean isFavorite() {
        return isFavorite;
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

    public int getSold() {
        return sold;
    }
}
