package com.whynotquang.ungdungmuadocongsonam_ltmt12.api;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Category;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ResponseUser;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    ///register
    @POST("register")
    @FormUrlEncoded
    Call<User> postRegister(@Field("email") String email,
                            @Field("full_name") String full_name,
                            @Field("phone_number") String phone_number,
                            @Field("password") String password);

    ////changePassword
    @FormUrlEncoded
    @PUT("change-password")
    Call<ResponseUser> updateExcute(
            @Field("oldPassword")String oldPassword,
            @Field("newPassword") String newPassword,
            @Header("Authorization") String authtoken
    );

    //get profile
    @GET("profile")
    Call<User> getProfile(@Header("Authorization") String authtoken);
    //logout
    @POST("logout")
    Call<User> postLogout(@Header("Authorization") String authtoken);
}
