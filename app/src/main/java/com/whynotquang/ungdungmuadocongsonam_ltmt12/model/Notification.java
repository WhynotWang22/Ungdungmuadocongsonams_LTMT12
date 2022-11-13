package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {
    public String _id;
    public String title;
    @SerializedName("body")
    @Expose
    public String desc;
    @SerializedName("image")
    @Expose
    public String images;
    public String time;

    public Notification() {
    }

    public Notification(String _id, String title, String desc, String images, String time) {
        this._id = _id;
        this.title = title;
        this.desc = desc;
        this.images = images;
        this.time = time;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
