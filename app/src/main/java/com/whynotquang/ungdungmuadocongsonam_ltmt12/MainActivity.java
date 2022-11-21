package com.whynotquang.ungdungmuadocongsonam_ltmt12;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.LoginActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.AccountFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.CartFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.CategoryFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.HomeFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.NotificationFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    boolean switch_noti;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // main activity
        navigationView = findViewById(R.id.navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
                        return true;
                    case R.id.nav_category:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CategoryFragment()).commit();
                        return true;
                    case R.id.nav_cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new CartFragment()).commit();
                        return true;
                    case R.id.nav_noti:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new NotificationFragment()).commit();
//                        navigationView.setItemIconTintList(null);
                        return true;
                    case R.id.nav_account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new AccountFragment()).commit();
                        return true;
                }
                return false;
            }
        });
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
        SharedPreferences sp1 = getApplicationContext().getSharedPreferences("switch", MODE_PRIVATE);
        switch_noti = sp1.getBoolean("value", true);
        getData(token);
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
                if (response.isSuccessful() && response.body() != null) {
                    if (switch_noti == true) {
                        //dk nhan all
                        FirebaseMessaging.getInstance().subscribeToTopic("all");
                        //dang ky nhan thong bao users
                        FirebaseMessaging.getInstance().subscribeToTopic(response.body().getId());
                    } else {
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("all");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(response.body().getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Không lấy được dữ liệu user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}