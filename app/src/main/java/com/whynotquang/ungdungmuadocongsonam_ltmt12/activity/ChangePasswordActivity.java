package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.MainActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ResponseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText ed_pass_current, ed_pass_moi, ed_nhap_lai_pass;
    Button btn_change_pass;
    String token = null;
    ImageButton btnback_changepass;
    ProgressBar spin_kit_changepassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ed_pass_current = findViewById(R.id.ed_pass_current);
        ed_pass_moi = findViewById(R.id.ed_pass_moi);
        ed_nhap_lai_pass = findViewById(R.id.ed_nhap_lai_pass);
        btn_change_pass = findViewById(R.id.btn_change_pass);
        btnback_changepass = findViewById(R.id.btnback_changepass);
        spin_kit_changepassword = findViewById(R.id.spin_kit_changepassword);
        ///
        Sprite threeBounce = new ThreeBounce();
        spin_kit_changepassword.setIndeterminateDrawable(threeBounce);
        getWindow().setStatusBarColor(getResources().getColor(R.color.bg_xam));
        spin_kit_changepassword.setVisibility(View.GONE);

        btnback_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        changePassword();
    }

    private void changePassword() {
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = ChangePasswordActivity.this.getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
                token = sp.getString("token", "");
                String oldPassword = ed_pass_current.getText().toString();
                String newPassword = ed_pass_moi.getText().toString();
                String nhaplaipassmoi = ed_nhap_lai_pass.getText().toString();

                if (oldPassword.isEmpty()) {
                    ed_pass_current.setError("Kh??ng ???????c ????? tr???ng");
                    ed_pass_current.requestFocus();
                    return;
                }
                if (newPassword.isEmpty()) {
                    ed_pass_moi.setError("kh??ng ???????c ????? tr???ng");
                    ed_pass_moi.requestFocus();
                    return;
                }
                if (nhaplaipassmoi.isEmpty()) {
                    ed_nhap_lai_pass.setError("kh??ng ???????c ????? tr???ng");
                    ed_nhap_lai_pass.requestFocus();
                    return;
                } else if (!newPassword.equals(nhaplaipassmoi)) {
                    Toast.makeText(ChangePasswordActivity.this, "M???t kh???u nh???p l???i kh??ng kh???p", Toast.LENGTH_SHORT).show();
                    return;
                }
                spin_kit_changepassword.setVisibility(View.VISIBLE);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstain.BASE_URL + "auth/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                Call<ResponseUser> call = apiService.updateExcute(oldPassword, newPassword, token);
                call.enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse
                            (Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()) {
                            ResponseUser userAPi = response.body();
//                          token = String.valueOf(userAPi.getUser().getTokens());
                            if (token != null) {
                                spin_kit_changepassword.setVisibility(View.GONE);
                                Toast.makeText(ChangePasswordActivity.this, "?????i m???t kh???u th??nh c??ng", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }
                        } else if (response.code() == 401) {
                            spin_kit_changepassword.setVisibility(View.GONE);
                            Toast.makeText(ChangePasswordActivity.this, "M???t kh???u c?? sai", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, "Vui l??ng ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}