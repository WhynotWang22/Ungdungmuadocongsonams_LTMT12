package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.MainActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class ThanksOrder_Activity extends AppCompatActivity {
    private Button btn_tieptucmuasam,btn_xemdonhang;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks_order);
        btn_tieptucmuasam = findViewById(R.id.btn_tieptucmuasam);
        btn_xemdonhang = findViewById(R.id.btn_xemdonhang);
        btn_tieptucmuasam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThanksOrder_Activity.this, MainActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        btn_xemdonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThanksOrder_Activity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }
}