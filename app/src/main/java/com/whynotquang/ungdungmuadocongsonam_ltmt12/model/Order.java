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
    public Date CreatedAt;
    public String _id;

    public Order() {
    }
}
