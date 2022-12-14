package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.MainActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CartAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductOrderAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    ImageButton btnback_chitiet_donhang;
    String id;
    ProgressBar progressBar;
    List<Products> productList;
    ProductOrderAdapter adapter;
    int soluong = 0;
    LinearLayout layout;
    Button btn_cancel_order;
    Button btn_call_shop;
    private static final int REQUEST_CALL = 1;

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
        layout = findViewById(R.id.layout_donhang_1);
        btn_call_shop = findViewById(R.id.btn_call_shop);
        btnback_chitiet_donhang = findViewById(R.id.btnback_chitiet_donhang);
        btn_cancel_order = findViewById(R.id.btn_cancel_order);
        recyclerView = findViewById(R.id.rc_view_product_orderdetail);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit_orderdetail);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        Log.d("aaa", "id: " + id);
        getData();
        btnback_chitiet_donhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn_cancel_order.setVisibility(View.GONE);
        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                postCacnel();
            }
        });
        btn_call_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callShop();
            }
        });
    }

    private void postCacnel() {
        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String token = sp1.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Order> call = apiService.postCannelOrder(token, id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    getData();
                    btn_cancel_order.setVisibility(View.GONE);
                    Toast.makeText(OrderDetailActivity.this, "H???y th??nh c??ng", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OrderDetailActivity.this, "H???y kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(OrderDetailActivity.this, "Vui l??ng ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
            }
        });

    }

    String phone;

    private void callShop() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getPhoneAdmin().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    phone = (response.body().getPhoneNumber());
                    if (ContextCompat.checkSelfPermission(OrderDetailActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(OrderDetailActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else {
                        String dial = "tel:" + phone;
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                    }
                }
//                Toast.makeText(OrderDetailActivity.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                //                Toast.makeText(OrderDetailActivity.this, "that bai", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callShop();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
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
                .baseUrl(AppConstain.BASE_URL + "order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Order> call = apiService.getDetailOrder(id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    Order order = response.body();

                    if (order.getStatus().equalsIgnoreCase("??ang ch??? x??c nh???n")) {
                        layout.setBackgroundColor(getResources().getColor(R.color.color_maucam));
                        btn_cancel_order.setVisibility(View.VISIBLE);
                    } else if (order.getStatus().equalsIgnoreCase("??ang chu???n b??? h??ng")) {
                        layout.setBackgroundColor(getResources().getColor(R.color.color_maucam));
                        btn_cancel_order.setVisibility(View.GONE);
                    } else if (order.getStatus().equalsIgnoreCase("??ang giao h??ng")) {
                        layout.setBackgroundColor(getResources().getColor(R.color.color_maucam));
                        btn_cancel_order.setVisibility(View.GONE);
                    } else if (order.getStatus().equalsIgnoreCase("Giao h??ng th??nh c??ng")) {
                        layout.setBackgroundColor(getResources().getColor(R.color.color_xanh));
                        btn_cancel_order.setVisibility(View.GONE);
                    } else if (order.getStatus().equalsIgnoreCase("Ng?????i d??ng ???? h???y ????n h??ng")) {
                        layout.setBackgroundColor(getResources().getColor(R.color.color_do));
                        btn_cancel_order.setVisibility(View.GONE);
                    }
                    if (order.getStatus().equalsIgnoreCase("ng?????i d??ng ???? h???y ????n h??ng")) {
                        tv_trangthai_donhangchitiet.setText("????n h??ng ???? h???y");
                    } else {
                        tv_trangthai_donhangchitiet.setText(order.getStatus());
                    }
                    tv_name_orderdetail.setText(order.getName());
                    tv_sdt_orderdetail.setText(String.valueOf(order.getPhoneNumber()));
                    tv_diachi_orderdetail.setText(order.getAddress());
                    tv_madon_orderdetail.setText(order.get_id());
                    tv_time_orderdetail.setText(getFormattedDate(order.getCreatedAt()));
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tv_tongtien_orderdetail.setText(decimalFormat.format(order.getTotal()) + "??");
                    tv_thanhtoan_orderdetail.setText(order.getPaymentMethodType());

                    List<Products> data = order.getProducts();
                    for (Products products : data) {
                        productList.add(products);
                        adapter.notifyDataSetChanged();
                    }
                    int Soluong = 0;
                    for (int i = 0; i < productList.size(); i++) {
                        Soluong += productList.get(i).quantity;
                    }
                    tv_soluong_orderdetail.setText(String.valueOf(Soluong));

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(OrderDetailActivity.this, "Kh??ng c?? d??? li???u", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(OrderDetailActivity.this, "Vui l??ng ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFormattedDate(Date timeZone) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String formattedDate = df.format(timeZone);
        return formattedDate;
    }
}