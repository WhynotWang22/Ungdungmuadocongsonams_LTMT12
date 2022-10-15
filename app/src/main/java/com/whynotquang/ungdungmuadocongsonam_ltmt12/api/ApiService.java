package com.whynotquang.ungdungmuadocongsonam_ltmt12.api;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
public interface ApiService {
    @GET("getall")
    Call<List<Banner>> getBanner();
}
