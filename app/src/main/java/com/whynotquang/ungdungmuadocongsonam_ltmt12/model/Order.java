package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    public String userId;
    public String name;
    public String phoneNumber;
    public ArrayList<Products> products;
    public String address;
    public int Total;
    public String paymentMethodType;
    public boolean isPaid;
    public String status;
    public Date createdAt;
    public String _id;

    public Order() {
    }

    public Order(String userId, String name, String phoneNumber, ArrayList<Products> products, String address, int total, String paymentMethodType, boolean isPaid, String status, Date createdAt, String _id) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.products = products;
        this.address = address;
        Total = total;
        this.paymentMethodType = paymentMethodType;
        this.isPaid = isPaid;
        this.status = status;
        this.createdAt = createdAt;
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public void setPaymentMethodType(String paymentMethodType) {
        this.paymentMethodType = paymentMethodType;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
