package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class ImageProductActivity extends AppCompatActivity {
    ImageView img_back,anh_product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_product);
        img_back = findViewById(R.id.btnback_img_product);
        anh_product = findViewById(R.id.anh_product);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Glide.with(ImageProductActivity.this).load(url).into(anh_product);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
    }
}