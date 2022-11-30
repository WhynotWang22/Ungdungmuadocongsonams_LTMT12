package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.NotificationAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.ProductAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Notification;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    List<Notification> notificationList;
    NotificationAdapter adapter;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = view.findViewById(R.id.rc_view_noti);
        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit_notification);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        notificationList = new ArrayList<>();
        getNoti();

        adapter = new NotificationAdapter(getActivity(),notificationList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }
    private void getNoti() {
        SharedPreferences sp = getContext().getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/notification/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Notification>> call = apiService.getNoti(token);
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.body() != null){
                    progressBar.setVisibility(View.GONE);
                    notificationList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Không lấy được dữ liệu sản phẩm ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}