package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class NotificationSettingActivity extends AppCompatActivity {

    ImageButton btn_NotificationSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);
        btn_NotificationSetting = findViewById(R.id.btn_NotificationSetting);
        btn_NotificationSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}