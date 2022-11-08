package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.InterFace.CallBack_Update_Delete;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CartAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ProductAddCart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartFragment extends Fragment {
    private static CallBack_Update_Delete callBackUpdateDelete;
    double giatien = 0;
    String id;
    RecyclerView rc_cart;
    private CartAdapter cartAdapter;
    private Bundle bundle;
    List<Products> productList;
    List<Products> productDeleteCart;
    private ImageView iconNoCart;
    private TextView titleNoCart;
    private TextView tv_total_product_cart;
    private TextView tvPriceDeliveryCart;
    private TextView tvTotalCart;
    private Button btnCheckoutCart, btnvalues;
    private LinearLayout layoutCart;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rc_cart = view.findViewById(R.id.rc_cart);
        iconNoCart = (ImageView) view.findViewById(R.id.iconNoCart);
        titleNoCart = (TextView) view.findViewById(R.id.titleNoCart);
        ///
        tv_total_product_cart = (TextView) view.findViewById(R.id.tv_total_product_cart);
        tvPriceDeliveryCart = (TextView) view.findViewById(R.id.tv_price_delivery_cart);
        tvTotalCart = (TextView) view.findViewById(R.id.tv_total_cart);
        btnCheckoutCart = (Button) view.findViewById(R.id.btn_checkout_cart);
        layoutCart = (LinearLayout) view.findViewById(R.id.layout_cart);
        Intent intent = getActivity().getIntent();
        id = intent.getStringExtra("id");


//        id = Hawk.get("id");

        getdataCart();
        callback();
        return view;
    }


    private void getdataCart() {
        SharedPreferences sp = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
//        Log.d("eeee", token);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "cart/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ProductAddCart> call = apiService.getlistCart(token);
        call.enqueue(new Callback<ProductAddCart>() {
            @Override
            public void onResponse(Call<ProductAddCart> call, Response<ProductAddCart> response) {
                if (response.body() != null) {
                    ///set recyclerview
                    productList = new ArrayList<>();
                    CartAdapter cartAdapter = new CartAdapter(productList, getContext(), callBackUpdateDelete);
                    rc_cart.setAdapter(cartAdapter);
                    cartAdapter.notifyDataSetChanged();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rc_cart.setLayoutManager(linearLayoutManager);
                    //add item
                    productList.addAll(response.body().getProducts());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
//                    tvTotalCart.setText(decimalFormat.format(response.body().getProducts().get(0).getPrice()) + "đ");
                    tv_total_product_cart.setText(decimalFormat.format(response.body().getTotal()) + "đ");
                    giatien = response.body().getTotal();
                    giatien += 15000;
                    tvTotalCart.setText(decimalFormat.format(giatien) + "đ");

                    Log.d("eee", "eee" + giatien);
                    //an text
                    iconNoCart.setVisibility(View.GONE);
                    titleNoCart.setVisibility(View.GONE);


                } else {
                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                    iconNoCart.setVisibility(View.VISIBLE);
                    titleNoCart.setVisibility(View.VISIBLE);
                    layoutCart.setVisibility(View.GONE);
                    //

                }
            }

            @Override
            public void onFailure(Call<ProductAddCart> call, Throwable t) {
                Toast.makeText(getContext(), "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show();
                layoutCart.setVisibility(View.GONE);
            }
        });
    }

    public void callback() {
        callBackUpdateDelete = new CallBack_Update_Delete() {
            @Override
            public void onclickDelete(Products products) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstain.BASE_URL + "cart/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                Call<List<Products>> call = apiService.deletelistCart(id);
                Log.d("eeeee", "eeeeeeeee" + id);
                call.enqueue(new Callback<List<Products>>() {
                    @Override
                    public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                        Toast.makeText(getContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<Products>> call, Throwable t) {
                        Toast.makeText(getContext(), "that bai", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void tong(int tong) {
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
                tv_total_product_cart.setText(decimalFormat.format(tong) + "đ");
                tong += 15000;
                tvTotalCart.setText(decimalFormat.format(tong) + "đ");
            }
        };
    }
}
