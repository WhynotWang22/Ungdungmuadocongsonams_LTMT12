package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductCategoryAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductCategoryActivity extends AppCompatActivity {

    RecyclerView rc_productcategory;
    List<Product> productList;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        rc_productcategory = findViewById(R.id.rc_productcategory);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getProductCategory();
    }

    private void getProductCategory() {
        productList = new ArrayList<>();
        ProductCategoryAdapter productCategoryAdapter = new ProductCategoryAdapter(productList,getApplicationContext());
        rc_productcategory.setAdapter(productCategoryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        rc_productcategory.setLayoutManager(gridLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "categorys/" )
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Product>> call = apiService.getAllproductbycategory(id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() != null){
                    productList.addAll(response.body());
                }
                productCategoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductCategoryActivity.this, "loi click iem", Toast.LENGTH_SHORT).show();
            }
        });

    }
}