package com.whynotquang.ungdungmuadocongsonam_ltmt12.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendemailActivity extends AppCompatActivity {


    private EditText edSendEmail;
    private Button btnSendEmail;
    String token = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendemail);

        edSendEmail = (EditText) findViewById(R.id.ed_send_email);
        btnSendEmail = (Button) findViewById(R.id.btn_send_email);
//        sendemail();
    }

//    private void sendemail() {
//        btnSendEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences sp = SendemailActivity.this.getApplicationContext().getSharedPreferences("Sendemail", MODE_PRIVATE);
//                token = sp.getString("token","");
//                String  sendEmail= edSendEmail.getText().toString();
//
//                if (sendEmail.isEmpty()){
//                    edSendEmail.setError("Không được để trống");
//                    edSendEmail.requestFocus();
//                    return;
//                }
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl("http://192.168.1.101:3000/api/auth/")
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                ApiService apiService = retrofit.create(ApiService.class);
//                Call<User> call = apiService.postSendEmail( sendEmail,token);
//                call.enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        if (response.isSuccessful()){
//                            User userAPi = response.body();
////                           token = String.valueOf(userAPi.getUser().token);
//                            if (token !=null){
//                                SharedPreferences sp= getSharedPreferences("change-password", MODE_PRIVATE);
//                                SharedPreferences.Editor Ed= sp.edit();
//                                Ed.putString("token",token);
//                                Ed.putString("email",sendEmail);
//                                Ed.commit();
//                                Intent intent = new Intent(SendemailActivity.this, FotgotPassChangeActivity.class);
//                                startActivity(intent);
//                                finishAffinity();
//                                Toast.makeText(SendemailActivity.this, "thành công", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//                        Toast.makeText(SendemailActivity.this, "That bai", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//            }
    }
