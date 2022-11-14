package com.whynotquang.ungdungmuadocongsonam_ltmt12.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductComment {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("AVG")
    @Expose
    private Integer avg;
    @SerializedName("dem")
    @Expose
    private Integer dem;
    @SerializedName("ProductItems")
    @Expose
    private List<Comment> productItems;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getAvg() {
        return avg;
    }

    public void setAvg(Integer avg) {
        this.avg = avg;
    }

    public Integer getDem() {
        return dem;
    }

    public void setDem(Integer dem) {
        this.dem = dem;
    }

    public List<Comment> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<Comment> productItems) {
        this.productItems = productItems;
    }
}
