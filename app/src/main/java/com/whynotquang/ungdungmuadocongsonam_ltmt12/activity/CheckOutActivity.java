package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
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
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.DiaChiAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Address;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.AddressItem;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.interFace.ItemClickAddressListener;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class CheckOutActivity extends AppCompatActivity {
    TextView tv_so_sanpham;
    TextView tv_gia_checkout;
    TextView tv_fee_ship_checkout;
    TextView tv_tongtien_checkout,tv_them_dia_chi;
    Button btn_thanhtoan;
    RecyclerView rc_view_diachi;
    ProgressBar progressBar;
    String token;
    List<Products> productsList;
    List<Address> addressList;
    int soluong_sanpham = 0;
    int feeship = 15000;
    String id,name,phoneNumber,diachi;
    String idUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        tv_so_sanpham = findViewById(R.id.tv_so_sanpham);
        tv_gia_checkout = findViewById(R.id.tv_gia_checkout);
        tv_fee_ship_checkout = findViewById(R.id.tv_fee_ship_checkout);
        tv_tongtien_checkout = findViewById(R.id.tv_tongtien_checkout);
        tv_them_dia_chi = findViewById(R.id.tv_them_dia_chi);
        rc_view_diachi = findViewById(R.id.rc_view_diachi);

        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit_checkout);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);
        productsList = new ArrayList<>();
        addressList = new ArrayList<>();

        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        token = sp1.getString("token", "");
        getData();
        getDataAddress();
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                postOrder();
            }
        });
        tv_them_dia_chi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this,AddressActivity.class);
                startActivity(intent);
            }
        });
    }
    //Get token through MoMo app

    private void getDataAddress() {
        DiaChiAdapter adapter = new DiaChiAdapter(addressList, new ItemClickAddressListener() {
            @Override
            public void onClickAddress(Address address) {
                name = address.getName();
                diachi = address.getDetailAddress();
                phoneNumber = String.valueOf(address.getNumberPhone());
            }
        });
        rc_view_diachi.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CheckOutActivity.this,RecyclerView.VERTICAL,false);
        rc_view_diachi.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "address/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<AddressItem> call = apiService.getAddress(token);
        call.enqueue(new Callback<AddressItem>() {
            @Override
            public void onResponse(Call<AddressItem> call, Response<AddressItem> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    AddressItem gioHang = response.body();
                    List<Address> datas = gioHang.getAddress();
                    //dung for de doc array
                    for (Address data : datas) {
                        addressList.add(data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressItem> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CheckOutActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "cart/")
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
                    idUser = gioHang.getUserId();
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
        if (name==null || diachi==null || phoneNumber==null ){
            progressBar.setVisibility(View.GONE);
            Toast.makeText(CheckOutActivity.this, "Vui lòng chọn địa chỉ", Toast.LENGTH_SHORT).show();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Order> call = apiService.postOrder(token,id,name,phoneNumber,diachi);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(CheckOutActivity.this,ThanksOrder_Activity.class);
                    startActivity(intent);
                    finishAffinity();
//                    Toast.makeText(CheckOutActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
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