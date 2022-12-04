package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class WelcomeActivity extends AppCompatActivity {

    Button btn_login_splash;
    TextView txt_signup_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btn_login_splash = findViewById(R.id.btn_login_splash);
        txt_signup_splash = findViewById(R.id.txt_signup_splash);

        getWindow().setStatusBarColor(getResources().getColor(R.color.color_login_sign_up));

        btn_login_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        txt_signup_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, Sign_Activity.class);
                startActivity(intent);
            }
        });
    }
}