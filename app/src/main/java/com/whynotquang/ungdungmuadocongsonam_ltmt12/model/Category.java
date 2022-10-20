package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

public class Category {
    public String _id;
    public String title;
    public String CateImg;

    public Category() {
    }

    public Category(String _id, String title, String cateImg) {
        this._id = _id;
        this.title = title;
        CateImg = cateImg;
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

    public String getCateImg() {
        return CateImg;
    }

    public void setCateImg(String cateImg) {
        CateImg = cateImg;
    }
}
