package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

public class HomeDanhMuc {
    private String Ten;
    private int anhsp;

    public HomeDanhMuc(String ten, int anhsp) {
        Ten = ten;
        this.anhsp = anhsp;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public int getAnhsp() {
        return anhsp;
    }

    public void setAnhsp(int anhsp) {
        this.anhsp = anhsp;
    }
}
