package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeNumberActivity extends AppCompatActivity {

    EditText ed_sdt_moi, ed_nhap_lai_sdt;
    Button btn_change_sdt;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_number);
        ed_sdt_moi = findViewById(R.id.ed_sdt_moi);
        ed_nhap_lai_sdt = findViewById(R.id.ed_nhap_lai_sdt);
        btn_change_sdt = findViewById(R.id.btn_change_sdt);

        progressBar = (ProgressBar) findViewById(R.id.spin_kit_change_phone_number);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.GONE);

        btn_change_sdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sdt = ed_sdt_moi.getText().toString().trim();
                String sdt2 = ed_nhap_lai_sdt.getText().toString().trim();
                if (sdt.isEmpty() || sdt2.isEmpty()) {
                    Toast.makeText(ChangeNumberActivity.this, "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sdt.length() < 10) {
                    Toast.makeText(ChangeNumberActivity.this, "Số điện thoại phải 10 số", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sdt2.length() < 10) {
                    Toast.makeText(ChangeNumberActivity.this, "Số điện thoại phải 10 số", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!sdt.equals(sdt2)) {
                    Toast.makeText(ChangeNumberActivity.this, "Số điện thoại nhập lại không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!validatePhone(sdt)) {
                    Toast.makeText(ChangeNumberActivity.this, "Số điện thoại phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!validatePhone(sdt2)) {
                    Toast.makeText(ChangeNumberActivity.this, "Số điện thoại phải là số", Toast.LENGTH_SHORT).show();
                    return;
                }
                putData(sdt2);
            }
        });
    }

    private void putData(String sdt2) {
        SharedPreferences sp = ChangeNumberActivity.this.getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token","");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.putPhoneNumber(token, sdt2);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ChangeNumberActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangeNumberActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ChangeNumberActivity.this, "Loi api", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validatePhone(String phone) {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(phone).matches();
    }
}