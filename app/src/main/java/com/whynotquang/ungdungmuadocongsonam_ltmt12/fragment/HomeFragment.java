package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.BannerAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CategoryAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductCategoryAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductSearchAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.RecommendAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Category;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.ultil.CheckConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    SliderView img_slide;
    RecyclerView rc_view_duocdexuat,rc_view_danhmuc,rc_view_topsell;
    AutoCompleteTextView ed_search;
    ImageButton imageButton, btn_close_search;
    RelativeLayout layout_search;
    List<Product> productListTopSell;
    List<Category> categoryList;
    List<Product> productList;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        img_slide = view.findViewById(R.id.img_slidebanner);
        rc_view_duocdexuat = view.findViewById(R.id.rc_view_duocdexuat);
        rc_view_danhmuc = view.findViewById(R.id.rc_view_danhmuc);
        rc_view_topsell = view.findViewById(R.id.rc_view_aopolo);
        ed_search = view.findViewById(R.id.ed_search);
        imageButton = view.findViewById(R.id.btn_search);
        btn_close_search = view.findViewById(R.id.btn_close_search);
        layout_search = view.findViewById(R.id.layout_search);
        CheckInternet();
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.mau_xam));
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
        ProductSearchAdapter adapter = new ProductSearchAdapter(getActivity(), android.R.layout.activity_list_item, productListTopSell);
        ed_search.setAdapter(adapter);
    }
    private void CheckInternet(){
        if (CheckConnection.haveNetwordConnection(getContext())){
            SliderPhoto();
            getDexuatchoban();
            getListCategory();
            getTop10();
        }else {
            CheckConnection.showToast_Short(getContext(),"Kiểm Tra kết nối của bạn");
        }
    }
    private void getTop10() {
        productList = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(getContext(),productList);
        rc_view_topsell.setAdapter(productAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rc_view_topsell.setLayoutManager(linearLayoutManager);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Product>> call = apiService.getTop10();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() !=null){
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getListCategory() {
        categoryList = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        rc_view_danhmuc.setAdapter(categoryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL, false);
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
                if (response.body() != null){
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

    private void getDexuatchoban() {
        productListTopSell = new ArrayList<>();
        RecommendAdapter recommendAdapter = new RecommendAdapter(getContext(), productListTopSell);
        rc_view_duocdexuat.setAdapter(recommendAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rc_view_duocdexuat.setLayoutManager(gridLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Product>> call = apiService.getProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() != null){
                    productListTopSell.addAll(response.body());
                    recommendAdapter.notifyDataSetChanged();
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