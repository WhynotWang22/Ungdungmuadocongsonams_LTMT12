package com.whynotquang.ungdungmuadocongsonam_ltmt12.api;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Category;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ResponseUser;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
                            @Field("password") String password,
                            @Field("address") String diachi
                            );


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
                            @Field("quantity") int quantity,
                            @Field("size") String size,
                            @Field("color") String color);
    //update sdt
    @FormUrlEncoded
    @PUT("edit-phone-number")
    Call<User> putPhoneNumber(@Header("Authorization") String authtoken,
                            @Field("phone_number") String phone_number
    );
    //update profile
    @Multipart
    @PUT("edits")
    Call<User> putEditProfile(@Header("Authorization") String authtoken,
                              @Part("full_name") RequestBody full_name,
                              @Part("email") RequestBody email,
                              @Part("phone_number") RequestBody phone_number,
                              @Part("address") RequestBody diachi,
                              @Part MultipartBody.Part avatar
    );
    //get list cart
    @GET("list")
    Call<Cart> getCart(@Header("Authorization") String authtoken);
    //delete cart
    @DELETE("delete/{id}")
    Call<Products> deleteCart(@Header("Authorization") String authtoken,@Path("id") String id);
    //update soluong
    @FormUrlEncoded
    @PUT("update/{id}")
    Call<Products> updateCart(@Header("Authorization") String authtoken,@Path("id") String id,@Field("quantity") int quantity);

    @POST("createCashOrder/{id}")
    Call<Order> postOrder(@Header("Authorization") String authtoken,
                          @Path("id") String id);


    ////changePassword
    @FormUrlEncoded
    @PUT("change-password")
    Call<ResponseUser> updateExcute(
            @Field("oldPassword")String oldPassword,
            @Field("newPassword") String newPassword,
            @Header("Authorization") String authtoken
    );
}
