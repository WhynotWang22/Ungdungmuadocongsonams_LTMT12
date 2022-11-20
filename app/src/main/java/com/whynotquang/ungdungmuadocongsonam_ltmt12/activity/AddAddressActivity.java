package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Address;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAddressActivity extends AppCompatActivity {

    EditText ed_address_addaddress,ed_name_addaddress,ed_sdt_addaddress;
    Button btn_add_address;
    ProgressBar progressBar;
    ImageButton btnback_add_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ed_name_addaddress = findViewById(R.id.ed_name_addaddress);
        ed_sdt_addaddress = findViewById(R.id.ed_sdt_addaddress);
        ed_address_addaddress = findViewById(R.id.ed_address_addaddress);
        btn_add_address = findViewById(R.id.btn_add_address);
        btnback_add_address = findViewById(R.id.btnback_add_address);
        progressBar = (ProgressBar) findViewById(R.id.spin_kit_add_address);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.GONE);
        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name_addaddress.getText().toString().trim();
                String sdt = ed_sdt_addaddress.getText().toString().trim();
                String address = ed_address_addaddress.getText().toString().trim();
                if (name.isEmpty() || address.isEmpty()) {
                    Toast.makeText(AddAddressActivity.this, "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sdt.length() < 10) {
                    Toast.makeText(AddAddressActivity.this, "Số điện thoại phải 10 số", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!validatePhone(String.valueOf(sdt))) {
                    Toast.makeText(AddAddressActivity.this, "Số điện thoại phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                postAddress(name,address, Integer.parseInt(sdt));
            }
        });
        btnback_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void postAddress(String name, String address, int sdt) {
        SharedPreferences sp = AddAddressActivity.this.getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token","");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/address/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Address> call = apiService.postAddress(token,name,address,sdt);
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    ed_name_addaddress.setText("");
                    ed_sdt_addaddress.setText("");
                    ed_address_addaddress.setText("");
                    Toast.makeText(AddAddressActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddAddressActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddAddressActivity.this, "Loi api", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validatePhone(String phone) {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(phone).matches();
    }
}