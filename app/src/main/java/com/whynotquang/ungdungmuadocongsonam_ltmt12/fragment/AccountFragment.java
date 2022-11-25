package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.AddAddressActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.AddressActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.LoginActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.OrderActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ProfileActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.SettingActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.StoreActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {

    TextView tv_name, tv_email;
    RelativeLayout btn_my_order, btn_diachi, btn_phuongthucthanhtoan, btn_setting, btn_privacy_setting;
    Button btn_logout;
    ImageView avt;
    String token;
    SharedPreferences sp;
    LinearLayout liner_profile;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        avt = view.findViewById(R.id.avt);
        btn_my_order = view.findViewById(R.id.btn_my_order);
        btn_diachi = view.findViewById(R.id.btn_diachi);
        btn_phuongthucthanhtoan = view.findViewById(R.id.btn_phuongthucthanhtoan);
        btn_setting = view.findViewById(R.id.btn_setting);
        btn_privacy_setting = view.findViewById(R.id.btn_privacy_setting);
        btn_logout = view.findViewById(R.id.btn_logout);
        liner_profile = view.findViewById(R.id.liner_profile);
        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit_account);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        liner_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });
        btn_diachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddressActivity.class);
                getActivity().startActivity(intent);
            }
        });
        btn_my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                getActivity().startActivity(intent);
            }
        });
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SettingActivity.class);
                getActivity().startActivity(intent);
            }
        });
        btn_privacy_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), StoreActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                logOut();
            }
        });
        sp = getContext().getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        token = sp.getString("token", "");
//        getData(token);
//        Log.d("aaa", "token: " + token);
        return view;
    }

    private void logOut() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<User> call = apiService.postLogout(token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finishAffinity();
                    SharedPreferences.Editor Ed = sp.edit();
                    Ed.clear();
                    Ed.commit();
                } else {
                    Toast.makeText(getContext(), "Đăng xuất không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi api đăng xuất", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
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
                    if (getActivity() == null) {
                        return;
                    }
                    tv_email.setText(response.body().getEmail());
                    tv_name.setText(response.body().getFullName());
                    Glide.with(getContext()).load(response.body().getAvatar()).into(avt);
                    progressBar.setVisibility(View.GONE);
                } else {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finishAffinity();
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("aaa","onStart");
        getData(token);
    }
}