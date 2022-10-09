package com.whynotquang.ungdungmuadocongsonam_ltmt12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.AccountFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.CartFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.CategoryFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.HomeFragment;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment.NotificationFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}