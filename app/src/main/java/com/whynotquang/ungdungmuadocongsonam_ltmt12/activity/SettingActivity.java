package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class SettingActivity extends AppCompatActivity {

    ImageButton btn_setting;
    LinearLayout linear_Change_Password,linear_Notification_Setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        btn_setting = findViewById(R.id.btn_setting);
        linear_Change_Password = findViewById(R.id.linear_Change_Password);
        linear_Notification_Setting = findViewById(R.id.linear_Notification_Setting);

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        linear_Change_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        linear_Notification_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this,NotificationSettingActivity.class);
                startActivity(intent);
            }
        });
    }
}