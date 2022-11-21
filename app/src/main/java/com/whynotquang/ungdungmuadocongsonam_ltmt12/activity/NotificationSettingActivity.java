package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.MainActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationSettingActivity extends AppCompatActivity {

    ImageButton btn_NotificationSetting;
    SwitchCompat switchCompat;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);
        switchCompat = findViewById(R.id.switch_noti);
        btn_NotificationSetting = findViewById(R.id.btn_NotificationSetting);
        btn_NotificationSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //set state switch
        SharedPreferences sharedPreferences = getSharedPreferences("switch", MODE_PRIVATE);
        switchCompat.setChecked(sharedPreferences.getBoolean("value", true));
        editor = getSharedPreferences("switch", MODE_PRIVATE).edit();
        //get id users
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
        getData(token);
    }

    private void getData(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.getProfile(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //dang ky nhan thong bao users
                    switchCompat.setOnCheckedChangeListener(
                            new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton,
                                                             boolean b) {
                                    if (switchCompat.isChecked() == true) {
                                        FirebaseMessaging.getInstance().subscribeToTopic("all");
                                        FirebaseMessaging.getInstance().subscribeToTopic(response.body().getId());
                                        editor.putBoolean("value", true);
                                        editor.apply();
                                        switchCompat.setChecked(true);
                                        Toast.makeText(NotificationSettingActivity.this, "Đã bật thông báo", Toast.LENGTH_SHORT).show();
                                    } else {
                                        FirebaseMessaging.getInstance().unsubscribeFromTopic("all");
                                        FirebaseMessaging.getInstance().unsubscribeFromTopic(response.body().getId());
                                        editor.putBoolean("value", false);
                                        editor.apply();
                                        switchCompat.setChecked(false);
                                        Toast.makeText(NotificationSettingActivity.this, "Đã tắt thông báo", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(NotificationSettingActivity.this, "Không lấy được dữ liệu user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}