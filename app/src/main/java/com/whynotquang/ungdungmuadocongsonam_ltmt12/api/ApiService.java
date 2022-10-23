package com.whynotquang.ungdungmuadocongsonam_ltmt12.api;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Category;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
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

    //login
    @FormUrlEncoded
    @POST("login")
    Call<User> postLogin(@Field("email") String email,@Field("password") String password);

    //get profile
    @GET("profile")
    Call<User> getProfile(@Header("Authorization") String authtoken);
}
