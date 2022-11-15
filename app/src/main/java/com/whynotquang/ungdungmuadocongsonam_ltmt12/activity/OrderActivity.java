package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class OrderActivity extends AppCompatActivity {

    RecyclerView RecyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        RecyclerView = findViewById(R.id.rc_view_order);

    }
}