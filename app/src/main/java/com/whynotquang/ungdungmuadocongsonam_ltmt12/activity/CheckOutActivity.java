package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckOutActivity extends AppCompatActivity {
    TextView tv_so_sanpham;
    TextView tv_gia_checkout;
    TextView tv_fee_ship_checkout;
    TextView tv_tongtien_checkout;
    Button btn_thanhtoan;
    ProgressBar progressBar;
    String token;
    List<Products> productsList;
    int soluong_sanpham = 0;
    int feeship = 15000;
    String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        tv_so_sanpham = findViewById(R.id.tv_so_sanpham);
        tv_gia_checkout = findViewById(R.id.tv_gia_checkout);
        tv_fee_ship_checkout = findViewById(R.id.tv_fee_ship_checkout);
        tv_tongtien_checkout = findViewById(R.id.tv_tongtien_checkout);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit_checkout);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        productsList = new ArrayList<>();

        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        token = sp1.getString("token", "");
        getData();
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                postOrder();
            }
        });
    }
    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/cart/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Cart> call = apiService.getCart(token);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Cart gioHang = response.body();
                    List<Products> datas = gioHang.getProducts();
                    //dung for de doc array
                    for (Products data : datas) {
                        productsList.add(data);
                    }
                    for (int i = 0; i < productsList.size(); i++) {
                        soluong_sanpham++;
                    }
                    id=response.body().get_id();
                    tv_so_sanpham.setText(soluong_sanpham+" sản phẩm)");
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tv_gia_checkout.setText(decimalFormat.format(Integer.parseInt(response.body().getTotal()))+"đ");
                    tv_fee_ship_checkout.setText(decimalFormat.format(feeship)+"đ");
                    int tongtien;
                    tongtien = Integer.parseInt(response.body().getTotal()) + feeship;
                    tv_tongtien_checkout.setText(decimalFormat.format(tongtien)+"đ");
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckOutActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void postOrder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Order> call = apiService.postOrder(token,id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CheckOutActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CheckOutActivity.this, "Đặt hàng không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckOutActivity.this, "Lỗi api không đặt hàng được", Toast.LENGTH_SHORT).show();
            }
        });
    }
}