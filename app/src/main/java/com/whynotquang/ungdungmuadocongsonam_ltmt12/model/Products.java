package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

public class Products {
    public String productId;
    public String color;
    public String size;
    public int quantity;
    public int amount;
    public String _id;

    public Products() {
    }

    public Products(String productId, String color, String size, int quantity, int amount, String _id) {
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.amount = amount;
        this._id = _id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
