package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class OrderFailed_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_failed);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                OrderFailed_Activity.this,R.style.BottomSheetDialogTheme
        );
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottomsheet_layout_oder_failed,
                        (LinearLayout)findViewById(R.id.bottomSheetcontainer));

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}