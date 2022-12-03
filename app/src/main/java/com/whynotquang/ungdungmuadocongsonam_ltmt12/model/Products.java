package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

public class Products {
    public String productId;
    public String title;
    public int price;
    public String size;
    public String color;
    public int quantity;
    public String ProductIMG;
    public String _id;

    public Products() {
    }

    public Products(String productId, String title, int price, String size, String color, int quantity, String productIMG, String _id) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        ProductIMG = productIMG;
        this._id = _id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductIMG() {
        return ProductIMG;
    }

    public void setProductIMG(String productIMG) {
        ProductIMG = productIMG;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

}
