package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangesAdressActivity extends AppCompatActivity {
    String token;
    private EditText edDiachiMoi;
    private EditText ed_xacnhandiachi_moi;
    private Button btnChangeAdress;
    private ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changes_adress);
        edDiachiMoi = (EditText) findViewById(R.id.ed_diachi_moi);
        ed_xacnhandiachi_moi = (EditText) findViewById(R.id.ed_xacnhandiachi_moi);
        btnChangeAdress = (Button) findViewById(R.id.btn_change_adress);
        progressBar = findViewById(R.id.spin_kit_changeAdress);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.GONE);
        btnChangeAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAdress();
            }
        });
    }

    private void updateAdress() {
        SharedPreferences sp = ChangesAdressActivity.this.getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        token = sp.getString("token", "");
        String newAdress = edDiachiMoi.getText().toString();
        String xnnewAdress = ed_xacnhandiachi_moi.getText().toString();
        if (newAdress.isEmpty()) {
            edDiachiMoi.setError("Không được để trống");
            edDiachiMoi.requestFocus();
            return;
        }
        if (xnnewAdress.isEmpty()) {
            ed_xacnhandiachi_moi.setError("không được để trống");
            ed_xacnhandiachi_moi.requestFocus();
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.7:3000/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<User>> call = apiService.putchangeAdress(token, newAdress);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
//                    User userAPi = response.body();
////                           token = String.valueOf(userAPi.getUser().token);
                    if (token != null) {
                        SharedPreferences sp = getSharedPreferences("change-password", MODE_PRIVATE);
                        SharedPreferences.Editor Ed = sp.edit();
                        Ed.putString("token", token);
                        Ed.putString("newAdress", newAdress);
                        Ed.putString("xnnewAdress", xnnewAdress);
                        Ed.commit();
                        Toast.makeText(ChangesAdressActivity.this, "Đổi Thất Bại", Toast.LENGTH_SHORT).show();

                    } else {
                        Log.e("error", "aaaaaaaaaaaaaaaaaaaaaa");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(ChangesAdressActivity.this, "Đổi Thành Công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangesAdressActivity.this, CheckOutActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
    private void getData(){

    }
}