package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.OrderAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Address;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.AddressItem;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Order> orderList;
    OrderAdapter adapter;
    ProgressBar progressBar;
    ImageButton btnback_order;
    RelativeLayout layout_check_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyclerView = findViewById(R.id.rc_view_order);
        layout_check_order = findViewById(R.id.layout_check_order);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit_donhangcuatoi);
        Sprite threeBounce = new ThreeBounce();
        btnback_order = findViewById(R.id.btnback_order);
        layout_check_order.setVisibility(View.GONE);
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        orderList = new ArrayList<>();
        adapter = new OrderAdapter(OrderActivity.this, orderList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(OrderActivity.this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        getData();
        btnback_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getData() {
        SharedPreferences sp = OrderActivity.this.getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Order>> call = apiService.getOrder(token);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    orderList.addAll(response.body());
                    recyclerView.getRecycledViewPool().clear();
                    adapter.notifyDataSetChanged();
                    if (orderList == null || orderList.size() == 0){
                        layout_check_order.setVisibility(View.VISIBLE);
                    }else {
                        layout_check_order.setVisibility(View.GONE);
                    }
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(OrderActivity.this, "Không lấy được dữ liệu đơn hàng", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        orderList.clear();
        getData();
    }
}