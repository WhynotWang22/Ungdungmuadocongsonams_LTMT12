package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText ed_email,ed_pass;
    Button button_login;
    String token = null;
    TextView tvDangkychuyenamn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_email = findViewById(R.id.ed_email_login);
        ed_pass = findViewById(R.id.ed_password_login);
        button_login = findViewById(R.id.btn_login);
        tvDangkychuyenamn = (TextView) findViewById(R.id.tv_dangkychuyenamn);
        progressBar = (ProgressBar)findViewById(R.id.spin_kit_login_in);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.GONE);

        chuyenamnhinh();

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg_xam));

        SharedPreferences sp = LoginActivity.this.getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String aaa = sp.getString("token","");
        Log.d("aaa","token logout: "+aaa);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ed_email.getText().toString().trim();
                String password = ed_pass.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!validateEmail(email))
                {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập email đúng định dạng", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password.length()<6)
                {
                    Toast.makeText(LoginActivity.this, "Mật khẩu trên 6 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                postData(email,password);
            }
        });
    }
    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void chuyenamnhinh() {
        tvDangkychuyenamn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,Sign_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void postData(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( AppConstain.BASE_URL + "auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.postLogin(email,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User userApi = response.body();
                    token = String.valueOf(userApi.getTokens());
                    if (token !=null){
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp= getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor Ed= sp.edit();
                        Ed.putString("email",userApi.getEmail());
                        Ed.putString("id",userApi.getId());
                        Ed.putString("token", String.valueOf(userApi.getToken()));
                        Ed.commit();
                    }
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi api login", Toast.LENGTH_SHORT).show();
            }
        });
    }

}