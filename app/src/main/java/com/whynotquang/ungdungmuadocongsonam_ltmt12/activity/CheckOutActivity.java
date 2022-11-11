package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ProductAddCart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckOutActivity extends AppCompatActivity {
    private String token;
    private SharedPreferences sp;
    private TextView tv_checkout_tongtien;
    private TextView tv_checkout_themdiachi;
    private TextView tv_checkout_tongsp;
    private TextView tv_checkoutdiachi;
    private Button btn_thanhtoan;
    private  List<Products> productList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        tv_checkout_themdiachi = findViewById(R.id.tv_checkout_themdiachi);
        tv_checkoutdiachi = findViewById(R.id.tv_checkoutdiachi);
        tv_checkout_tongtien = findViewById(R.id.tv_checkout_tongtien);
        tv_checkout_tongsp = findViewById(R.id.tv_checkout_tongsp);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);
        ///get token
        sp = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        token = sp.getString("token", "");
        tv_checkout_themdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this, ChangesAdressActivity.class);
                startActivity(intent);
            }
        });
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAddOrder();
            }
        });
        getDataCart();
        getAdress(token);


    }

    private void getDataCart() {
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
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
                    productList = new ArrayList<>();
                    //add item
                    productList.addAll(response.body().getProducts());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
                    tv_checkout_tongtien.setText(decimalFormat.format(response.body().getTotal())+ "đ");

                } else {
                    Toast.makeText(CheckOutActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ProductAddCart> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAdress(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.getProfile(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tv_checkoutdiachi.setText(response.body().getAddress());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, "Không lấy được dữ liệu user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postAddOrder() {
        List<ProductAddCart> productAddCartList;
        productAddCartList = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "cart/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService1 = retrofit1.create(ApiService.class);
        Call<ProductAddCart> call1 = apiService1.getlistCart(token);
        call1.enqueue(new Callback<ProductAddCart>() {
            @Override
            public void onResponse(Call<ProductAddCart> call, Response<ProductAddCart> response) {
                if (response.body() != null) {
                    productAddCartList.add(response.body());
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(AppConstain.BASE_URL + "order/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    ApiService apiService = retrofit.create(ApiService.class);
                    Call<List<ProductAddCart>> call2 = apiService.PostCartAddOrder(token, productAddCartList.get(0).get_id());
                    call2.enqueue(new Callback<List<ProductAddCart>>() {
                        @Override
                        public void onResponse(Call<List<ProductAddCart>> call, Response<List<ProductAddCart>> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(CheckOutActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CheckOutActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ProductAddCart>> call, Throwable t) {
                            Toast.makeText(CheckOutActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CheckOutActivity.this, ThanksOrder_Activity.class);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ProductAddCart> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getData() {
        Intent i = getIntent();
        String s = i.getStringExtra("key");
        tv_checkout_tongtien.setText(s);
    }
}