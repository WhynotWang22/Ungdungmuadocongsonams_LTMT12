package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CartAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductOrderAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderDetailActivity extends AppCompatActivity {

    TextView tv_trangthai_donhangchitiet, tv_name_orderdetail, tv_sdt_orderdetail, tv_diachi_orderdetail, tv_madon_orderdetail,
            tv_soluong_orderdetail, tv_time_orderdetail, tv_tongtien_orderdetail, tv_thanhtoan_orderdetail, tv_thanks_orderdetail;
    RecyclerView recyclerView;
    String id;
    ProgressBar progressBar;
    List<Products> productList;
    ProductOrderAdapter adapter;
    int soluong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        tv_trangthai_donhangchitiet = findViewById(R.id.tv_trangthai_donhangchitiet);
        tv_name_orderdetail = findViewById(R.id.tv_name_orderdetail);
        tv_sdt_orderdetail = findViewById(R.id.tv_sdt_orderdetail);
        tv_diachi_orderdetail = findViewById(R.id.tv_diachi_orderdetail);
        tv_madon_orderdetail = findViewById(R.id.tv_madon_orderdetail);
        tv_soluong_orderdetail = findViewById(R.id.tv_soluong_orderdetail);
        tv_time_orderdetail = findViewById(R.id.tv_time_orderdetail);
        tv_tongtien_orderdetail = findViewById(R.id.tv_tongtien_orderdetail);
        tv_thanhtoan_orderdetail = findViewById(R.id.tv_thanhtoan_orderdetail);
        tv_thanks_orderdetail = findViewById(R.id.tv_thanks_orderdetail);

        recyclerView = findViewById(R.id.rc_view_product_orderdetail);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit_orderdetail);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getData();
    }

    private void getData() {
        productList = new ArrayList<>();
        adapter = new ProductOrderAdapter(productList, OrderDetailActivity.this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDetailActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(OrderDetailActivity.this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String token = sp1.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://mofshop.shop/api/order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Order> call = apiService.getDetailOrder(token, id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    Order order = response.body();
                    tv_trangthai_donhangchitiet.setText(order.getStatus());
                    tv_name_orderdetail.setText(order.getName());
                    tv_sdt_orderdetail.setText(String.valueOf(order.getPhoneNumber()));
                    tv_diachi_orderdetail.setText(order.getAddress());
                    tv_madon_orderdetail.setText(order.get_id());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tv_tongtien_orderdetail.setText(decimalFormat.format(order.getTotal()) + "đ");
                    tv_thanhtoan_orderdetail.setText(order.getPaymentMethodType());

                    List<Products> data = order.getProducts();
                    for (Products products : data) {
                        productList.add(products);
                        adapter.notifyDataSetChanged();
                    }

                    for (int i = 0; i < productList.size(); i++) {
                        soluong++;
                    }
                    tv_soluong_orderdetail.setText(String.valueOf(soluong));

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OrderDetailActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(OrderDetailActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}