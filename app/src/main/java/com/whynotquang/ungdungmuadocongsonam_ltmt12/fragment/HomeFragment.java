package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ChitietActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.BannerAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CategoryAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductSearchAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Category;
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
    RecyclerView rc_view_duocdexuat, rc_view_danhmuc;
    AutoCompleteTextView ed_search;
    ImageButton imageButton, btn_close_search;
    RelativeLayout layout_search;
    //
    List<Product> productList;
    List<Category> categoryList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img_slide = view.findViewById(R.id.img_slidebanner);
        rc_view_duocdexuat = view.findViewById(R.id.rc_view_duocdexuat);
        rc_view_danhmuc = view.findViewById(R.id.rc_view_danhmuc);
        ed_search = view.findViewById(R.id.ed_search);
        imageButton = view.findViewById(R.id.btn_search);
        btn_close_search = view.findViewById(R.id.btn_close_search);
        layout_search = view.findViewById(R.id.layout_search);
        SliderPhoto();
        getListProduct();
        getListCategory();
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.color_bar));

        layout_search.setVisibility(View.GONE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_search.setVisibility(View.VISIBLE);
                ed_search.requestFocus();
            }
        });
        btn_close_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_search.setVisibility(View.GONE);
                ed_search.setText("");
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            }
        });
        setSanphamSearchAdapter();
        return view;
    }

    private void setSanphamSearchAdapter() {
        ProductSearchAdapter adapter = new ProductSearchAdapter(getActivity(), android.R.layout.activity_list_item, productList);
        ed_search.setAdapter(adapter);
    }

    private void getListCategory() {
        categoryList = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        rc_view_danhmuc.setAdapter(categoryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        rc_view_danhmuc.setLayoutManager(gridLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "categorys/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Category>> call = apiService.getCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.body() != null) {
                    categoryList.addAll(response.body());
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getContext(), "Không lấy được dữ liệu danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListProduct() {
        productList = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(getContext(), productList);
        rc_view_duocdexuat.setAdapter(productAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rc_view_duocdexuat.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Product>> call = apiService.getProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() != null) {
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Không lấy được dữ liệu sản phẩm ", Toast.LENGTH_SHORT).show();
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
                .baseUrl(AppConstain.BASE_URL + "banners/")
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