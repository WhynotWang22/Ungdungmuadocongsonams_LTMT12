package com.whynotquang.ungdungmuadocongsonam_ltmt12;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.AccountFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.CartFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.CategoryFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.HomeFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.NotificationFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // main activity
        navigationView = findViewById(R.id.navigation);

        FirebaseMessaging.getInstance().subscribeToTopic("all");

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

        
}
}