package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.MainActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activitys);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = SplashActivity.this.getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
                String token = sp.getString("token","");
                if (token.isEmpty()){
                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    getData(token);
                }
            }
        },1000);
    }
    private void getData(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.getProfile(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }else {
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finishAffinity();
                Toast.makeText(SplashActivity.this, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}