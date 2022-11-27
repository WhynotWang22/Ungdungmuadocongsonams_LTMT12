package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    EditText ed_pass_current,ed_pass_moi,ed_nhap_lai_pass;
    Button btn_change_pass;
    String token = null;
    ImageButton btnback_changepass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ed_pass_current = findViewById(R.id.ed_pass_current);
        ed_pass_moi = findViewById(R.id.ed_pass_moi);
        ed_nhap_lai_pass = findViewById(R.id.ed_nhap_lai_pass);
        btn_change_pass = findViewById(R.id.btn_change_pass);
        btnback_changepass = findViewById(R.id.btnback_changepass);
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
                token = sp.getString("token","");
                String  oldPassword= ed_pass_current.getText().toString();
                String newPassword = ed_pass_moi.getText().toString();
                String nhaplaipassmoi = ed_nhap_lai_pass.getText().toString();
                if (oldPassword.isEmpty()){
                    ed_pass_current.setError("Không được để trống");
                    ed_pass_current.requestFocus();
                    return;
                }
                if (newPassword.isEmpty()){
                    ed_pass_current.setError("không được để trống");
                    ed_pass_current.requestFocus();
                    return;
                }
                else if (!newPassword.equals(nhaplaipassmoi)) {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu  nhập lại không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstain.BASE_URL + "auth/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                Call<ResponseUser> call = apiService.updateExcute( oldPassword, newPassword,token);
                call.enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            ResponseUser userAPi = response.body();
//                           token = String.valueOf(userAPi.getUser().token);
                            if (token !=null){
                                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();
                                Toast.makeText(ChangePasswordActivity.this, "thành công", Toast.LENGTH_SHORT).show();

                            }else{
                                Log.e("error","aaaaaaaaaaaaaaaaaaaaaa");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}