package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {

    TextView tv_name,tv_email;
    RelativeLayout btn_my_order,btn_diachi,btn_phuongthucthanhtoan,btn_setting,btn_privacy_setting;
    Button btn_logout;
    String token;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        btn_my_order = view.findViewById(R.id.btn_my_order);
        btn_diachi = view.findViewById(R.id.btn_diachi);
        btn_phuongthucthanhtoan = view.findViewById(R.id.btn_phuongthucthanhtoan);
        btn_setting = view.findViewById(R.id.btn_setting);
        btn_privacy_setting = view.findViewById(R.id.btn_privacy_setting);
        btn_logout = view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "aaaaa", Toast.LENGTH_SHORT).show();
            }
        });
        SharedPreferences sp1 = getContext().getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        token = sp1.getString("token","");
        getData(token);
        Log.d("aaa","token: "+token);
        return  view;
    }

    private void getData(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.52:3000/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.getProfile(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    tv_email.setText(response.body().getEmail());
                    tv_name.setText(response.body().getFull_name());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}