package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_Activity extends AppCompatActivity {

    private EditText edthoten;
    private EditText edtEmail;
    private EditText edtSdt;
    private EditText edtMatkhau, ed_diachi;
    private TextView tv_dangnhap;
    private Button btnDangki;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        edthoten = (EditText) findViewById(R.id.edthoten);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtSdt = (EditText) findViewById(R.id.edtSdt);
        edtMatkhau = (EditText) findViewById(R.id.edtMatkhau);
        btnDangki = (Button) findViewById(R.id.btn_dangki);
        tv_dangnhap = findViewById(R.id.tv_dangnhapchuyenmanhinh);
        ed_diachi = (EditText) findViewById(R.id.ed_diachi);
        chuyenmanhinh();

        progressBar = (ProgressBar) findViewById(R.id.spin_kit_sign_up);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);

        getWindow().setStatusBarColor(getResources().getColor(R.color.bg_xam));
        progressBar.setVisibility(View.GONE);

        btnDangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = edthoten.getText().toString().trim();
                String phone_number = edtSdt.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String password = edtMatkhau.getText().toString().trim();
                String diachi = ed_diachi.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty() || full_name.isEmpty() || diachi.isEmpty() || phone_number.isEmpty()) {
                    Toast.makeText(Sign_Activity.this, "Kh??ng ???????c ????? tr???ng th??ng tin", Toast.LENGTH_SHORT).show();
                    return;
                }
//                else if (!isValidName(full_name)) {
//                    Toast.makeText(Sign_Activity.this, "T??n kh??ng ???????c s??? d???ng k?? t???", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                else if (!validateEmail(email)) {
                    Toast.makeText(Sign_Activity.this, "Email kh??ng ????ng ?????nh d???ng", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!validatePhone(phone_number)) {
                    Toast.makeText(Sign_Activity.this, "S??? ??i???n tho???i ph???i l?? s???", Toast.LENGTH_SHORT).show();
                    return;
                } else if (phone_number.length() < 10) {
                    Toast.makeText(Sign_Activity.this, "S??? ??i???n tho???i ph???i tr??n 10 s???", Toast.LENGTH_SHORT).show();
                    return;
                } else if (phone_number.length() > 10) {
                    Toast.makeText(Sign_Activity.this, "S??? ??i???n tho???i ch??? ???????c 10 s???", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.length() < 6) {
                    Toast.makeText(Sign_Activity.this, "M???t kh???u ph???i d??i h??n 6 k?? t???", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                postData(full_name, phone_number, email, password, diachi);
            }
        });
    }

    public boolean isValidName(String name) {
        return Pattern.compile("/^[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]*$/").matcher(name).matches();
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean validatePhone(String phone) {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(phone).matches();
    }

    private void chuyenmanhinh() {
        tv_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }


    private void postData(String full_name, String phone_number, String email, String password, String diachi) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiSignup = retrofit.create(ApiService.class);

        Call<User> call = apiSignup.postRegister(email, full_name, phone_number, password, diachi);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    edthoten.setText("");
                    edtEmail.setText("");
                    edtMatkhau.setText("");
                    edtEmail.setText("");
                    ed_diachi.setText("");
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Sign_Activity.this, "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Sign_Activity.this, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
                else if (response.code() == 409) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Sign_Activity.this, "Email n??y ???? ???????c ????ng k??", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (response.code() == 408) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Sign_Activity.this, "S??? ??i???n tho???i n??y ???? ???????c ????ng k??", Toast.LENGTH_SHORT).show();
                    return;
                }
                else  {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Sign_Activity.this, "????ng k?? kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Sign_Activity.this, "Vui l??ng ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
