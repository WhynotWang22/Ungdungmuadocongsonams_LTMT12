package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.AddressAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Address;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.AddressItem;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button button;
    AddressAdapter adapter;
    List<Address> addressList;
    ProgressBar progressBar;
    ImageButton btnback_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        recyclerView = findViewById(R.id.rc_view_sodiachi);
        button = findViewById(R.id.btn_address);
        btnback_address = findViewById(R.id.btnback_address);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit_address);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        getData();
        addressList = new ArrayList<>();
        adapter = new AddressAdapter(AddressActivity.this,addressList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddressActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressActivity.this,AddAddressActivity.class);
                startActivity(intent);
            }
        });
        btnback_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getData() {
        SharedPreferences sp = AddressActivity.this.getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token","");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/address/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<AddressItem> call = apiService.getAddress(token);
        call.enqueue(new Callback<AddressItem>() {
            @Override
            public void onResponse(Call<AddressItem> call, Response<AddressItem> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    AddressItem addressItem = response.body();
                    List<Address> datas = addressItem.getAddress();
                    //dung for de doc array
                    for (Address data : datas) {
                        addressList.add(data);
                        adapter.notifyDataSetChanged();
                    }
                }else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<AddressItem> call, Throwable t) {
                Toast.makeText(AddressActivity.this, "Không lấy được dư liệu địa chỉ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}