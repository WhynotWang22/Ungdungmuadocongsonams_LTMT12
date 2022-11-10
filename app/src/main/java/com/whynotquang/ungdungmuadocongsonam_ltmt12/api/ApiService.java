package com.whynotquang.ungdungmuadocongsonam_ltmt12.api;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Category;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ProductAddCart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ResponseUser;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
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

    //get category theo items
    @GET("getall/{id}")
    Call<List<Product>> getAllproductbycategory(@Path("id") String id);

    //get ao polo
    @GET("getall/634d16bfb1d500646457f11d")
    Call<List<Product>> getAoPolo();

    ////changePassword
    @FormUrlEncoded
    @PUT("change-password")
    Call<ResponseUser> updateExcute(
            @Field("oldPassword") String oldPassword,
            @Field("newPassword") String newPassword,
            @Header("Authorization") String authtoken
    );

    //login
    @FormUrlEncoded
    @POST("login")
    Call<User> postLogin(@Field("email") String email, @Field("password") String password);

    ///register
    @POST("register")
    @FormUrlEncoded
    Call<User> postRegister(@Field("email") String email,
                            @Field("full_name") String full_name,
                            @Field("phone_number") String phone_number,
                            @Field("password") String password,
                            @Field("address") String diachi
    );

    ///change pass after send link to your email
    @FormUrlEncoded
    @POST("forgot-password")
    Call<User> postSendEmail(
            @Field("email") String email,
            @Header("Authorization") String authtoken);

    //get profile
    @GET("profile")
    Call<User> getProfile(@Header("Authorization") String authtoken);

    //logout
    @POST("logout")
    Call<User> postLogout(@Header("Authorization") String authtoken);

    //add cart
    @FormUrlEncoded
    @POST("add")
    Call<Products> postCart(@Header("Authorization") String authtoken,
                            @Field("productId") String productId,
                            @Field("color") String color,
                            @Field("size") String size,
                            @Field("quantity") int quantity,
                            @Field("amount") int amount
    );

    ///get list cart
    @GET("list")
    Call<ProductAddCart> getlistCart(@Header("Authorization")
                                             String authtoken);


    ///delete item cart
    @DELETE("delete/{itemId}")
    Call<List<Products>> deletelistCart(@Header("Authorization") String authtoken,
                                        @Path("itemId") String itemId);


    ////tang so luong item cart
    @FormUrlEncoded
    @PUT("update/{itemId}")
    Call<List<Products>> PostUpdateItemCart(@Header("Authorization") String authtoken,
                                            @Path("itemId") String itemId,
                                            @Field("quantity") int quantity
    );

    ///add order
    @POST("createCashOrder/{cartId}")
    Call<List<ProductAddCart>> PostCartAddOrder(@Header("Authorization") String authtoken,
                                                @Path("cartId") String cartId
    );


}
