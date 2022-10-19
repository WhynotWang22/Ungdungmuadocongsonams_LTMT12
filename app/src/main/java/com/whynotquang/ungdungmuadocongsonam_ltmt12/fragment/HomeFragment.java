package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.BannerAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.Home_Adapter1;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.HomeDanhMuc;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    SliderView img_slide;
    RecyclerView rc_view_duocdexuat;
    List<Product> productList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img_slide = view.findViewById(R.id.img_slidebanner);
        rc_view_duocdexuat = view.findViewById(R.id.rc_view_duocdexuat);
        SliderPhoto();
        getListProduct();
        return view;
    }

    private void getListProduct() {
        productList = new ArrayList<>();
//        productList.add(new Product("aaa","aaa",123,"aaa","aaa","aaa","aaa",1,"https://bucket.nhanh.vn/store/662/ps/20220926/SM0267__7__thumb.jpg"));
//        productList.add(new Product("aaa","aaa",123,"aaa","aaa","aaa","aaa",1,"https://bucket.nhanh.vn/store/662/ps/20220926/SM0267__7__thumb.jpg"));
//        productList.add(new Product("aaa","aaa",123,"aaa","aaa","aaa","aaa",1,"https://bucket.nhanh.vn/store/662/ps/20220926/SM0267__7__thumb.jpg"));
//        productList.add(new Product("aaa","aaa",123,"aaa","aaa","aaa","aaa",1,"https://bucket.nhanh.vn/store/662/ps/20220926/SM0267__7__thumb.jpg"));
//        productList.add(new Product("aaa","aaa",123,"aaa","aaa","aaa","aaa",1,"https://bucket.nhanh.vn/store/662/ps/20220926/SM0267__7__thumb.jpg"));

        ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
        rc_view_duocdexuat.setAdapter(productAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rc_view_duocdexuat.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.52:3000/api/products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Product>> call = apiService.getProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() != null){
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Loi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SliderPhoto() {
        List<Banner> bannerList = new ArrayList<>();
        BannerAdapter bannerAdapter = new BannerAdapter(bannerList, getContext());
        img_slide.setSliderAdapter(bannerAdapter);
        img_slide.setIndicatorAnimation(IndicatorAnimationType.WORM);
        img_slide.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        img_slide.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        img_slide.setIndicatorSelectedColor(Color.BLACK);
        img_slide.setIndicatorUnselectedColor(Color.GRAY);
        img_slide.setScrollTimeInSec(3); //set scroll delay in seconds :
        img_slide.startAutoCycle();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.52:3000/api/banners/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Banner>> call = apiService.getBanner();
        call.enqueue(new Callback<List<Banner>>() {
            @Override
            public void onResponse(Call<List<Banner>> call, Response<List<Banner>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bannerList.addAll(response.body());
                    bannerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không lấy được dữ liệu banner", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Banner>> call, Throwable t) {

            }
        });
    }

}