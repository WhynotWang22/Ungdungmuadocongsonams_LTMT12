package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.smarteist.autoimageslider.SliderView;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChitietActivity extends AppCompatActivity {

    SliderView img_product;
    TextView tv_title_product_chitiet,tv_so_luot_review,tv_price_product_chitiet;
    RadioButton radioGroup_color,radioGroup_size;
    Button btn_themsanpham;
    List<Product> productList;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        tv_title_product_chitiet = findViewById(R.id.tv_title_product_chitiet);
        tv_price_product_chitiet = findViewById(R.id.tv_price_product_chitiet);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getDataProduct();
    }
    public void getDataProduct() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.52:3000/api/products/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Product> call = apiService.getDetailProduct(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.body() !=null){
                    tv_title_product_chitiet.setText(response.body().getTitle());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tv_price_product_chitiet.setText(decimalFormat.format(response.body().getPrice())+"đ");
                }else {
                    Toast.makeText(ChitietActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(ChitietActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}