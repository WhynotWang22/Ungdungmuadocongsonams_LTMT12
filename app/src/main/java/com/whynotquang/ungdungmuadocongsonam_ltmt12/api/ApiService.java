package com.whynotquang.ungdungmuadocongsonam_ltmt12.api;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Category;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("getall")
    Call<List<Banner>> getBanner();
    @GET("getall")
    Call<List<Product>> getProduct();

    @GET("getone/{id}")
    Call<Product> getDetailProduct(@Path("id") String id);


    @GET("getall")
    Call<List<Category>> getCategory();
}
