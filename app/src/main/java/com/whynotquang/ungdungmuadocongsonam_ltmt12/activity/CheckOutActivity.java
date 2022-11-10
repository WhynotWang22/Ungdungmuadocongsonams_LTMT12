package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class CheckOutActivity extends AppCompatActivity {
    private TextView tv_checkout_tongtien;
    private TextView tv_checkout_themdiachi;
    private TextView tv_checkout_tongsp;
    private TextView tv_checkoutdiachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        tv_checkout_themdiachi = findViewById(R.id.tv_checkout_themdiachi);
        tv_checkoutdiachi = findViewById(R.id.tv_checkoutdiachi);
        tv_checkout_tongtien = findViewById(R.id.tv_checkout_tongtien);
        tv_checkout_tongsp = findViewById(R.id.tv_checkout_tongsp);
        getData();

    }

    private void getData(){
        Intent i = getIntent();
        String s  = i.getStringExtra("key");
        tv_checkout_tongtien.setText(s);
    }
}