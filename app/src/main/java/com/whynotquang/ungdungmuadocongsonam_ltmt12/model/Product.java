package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Product {
    public String _id;
    public String title;
    public int price;
    public String desc;
    public String category;
    public ArrayList<String> sizes = null;
    public String color;
    public int stock;
    public String img;
    public ArrayList<String> img_product = null;

    public Product() {
    }

    public Product(String _id, String title, int price, String desc, String category, ArrayList<String> sizes, String color, int stock, String img, ArrayList<String> img_product) {
        this._id = _id;
        this.title = title;
        this.price = price;
        this.desc = desc;
        this.category = category;
        this.sizes = sizes;
        this.color = color;
        this.stock = stock;
        this.img = img;
        this.img_product = img_product;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<String> sizes) {
        this.sizes = sizes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ArrayList<String> getImg_product() {
        return img_product;
    }

    public void setImg_product(ArrayList<String> img_product) {
        this.img_product = img_product;
    }
}