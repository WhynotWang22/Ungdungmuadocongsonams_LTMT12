package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

public class Banner {
    public String _id;
    public String description;
    public String anh;

    public Banner() {
    }

    public Banner(String _id, String description, String anh) {
        this._id = _id;
        this.description = description;
        this.anh = anh;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
