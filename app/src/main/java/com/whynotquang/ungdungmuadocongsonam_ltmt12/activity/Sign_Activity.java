package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class Sign_Activity extends AppCompatActivity {

    private EditText edthoten;
    private EditText edtEmail;
    private EditText edtSdt;
    private EditText edtMatkhau;
    private TextView tv_dangnhap;
    private AppCompatCheckBox chkLuuMatKhau;
    private Button btnDangki;
    String token = null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        edthoten = (EditText) findViewById(R.id.edthoten);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtSdt = (EditText) findViewById(R.id.edtSdt);
        edtMatkhau = (EditText) findViewById(R.id.edtMatkhau);
        chkLuuMatKhau = (AppCompatCheckBox) findViewById(R.id.chkLuuMatKhau);
        btnDangki = (Button) findViewById(R.id.btn_dangki);
        tv_dangnhap = findViewById(R.id.tv_dangnhapchuyenmanhinh);
        chuyenmanhinh();
        dangky();
        SharedPreferences sp = Sign_Activity.this.getApplicationContext().getSharedPreferences("Signup", MODE_PRIVATE);
        String qqqq = sp.getString("token", "");


    }

    private void dangky() {
        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String full_name = edthoten.getText().toString().trim();
                String phone_number = edtSdt.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtMatkhau.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Sign_Activity.this, "Khong duoc bo trong", Toast.LENGTH_SHORT).show();
                    return;
                }
//                postData(full_name,phone_number,email,password);
            }
        });
    }

    private void chuyenmanhinh() {
        tv_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

//    ProgressBar progressBar;
//        progressBar = (ProgressBar)findViewById(R.id.spin_kit_sign_up);
//        Sprite threeBounce = new ThreeBounce();
//        progressBar.setIndeterminateDrawable(threeBounce);
//    }


//
//    private void postData(String  full_name, String phone_number, String email, String password){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.2:3000/api/auth/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiService apiSignup = retrofit.create(ApiService.class);
//
//        Call<User> call = apiSignup.postRegister(email,full_name,phone_number,password);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()){
//                    User userAPi = response.body();
//                    token = userAPi.getToken();
//                    if (token !=null){
//                        Intent intent = new Intent(Sign_Activity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finishAffinity();
//                        Toast.makeText(Sign_Activity.this, "Đăng ky thành công", Toast.LENGTH_SHORT).show();
//                        SharedPreferences sp= getSharedPreferences("register", MODE_PRIVATE);
//                        SharedPreferences.Editor Ed= sp.edit();
//                        Ed.putString("email",userAPi.getEmail() );
//                        Ed.putString("password",userAPi.get_id());
//                        Ed.putString("token",userAPi.getToken());
//                        Ed.putString("full_name",userAPi.get_id());
//                        Ed.putString("phone_number",userAPi.getToken());
//                        Ed.commit();
//                    }
//                }
//            }
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(Sign_Activity.this, "loi api", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    }
}