package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

public class Favourites {
    public String _id;
    public String userId;
    public String productId;
    public boolean isFavorite;
    public String createdAt;
    public String updatedAt;
    public int __v;

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
