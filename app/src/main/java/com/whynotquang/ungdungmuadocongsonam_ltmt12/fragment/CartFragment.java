package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.firebase.messaging.FirebaseMessaging;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.CheckOutActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CartAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment {

    RecyclerView rc_view_gio_hang;
    TextView tv_tongtiensp,tv_phivanchuyen,tv_tongtien,tv_soluong_sp;
    Button btn_thanhtoan;
    ProgressBar progressBar;

    CartAdapter adapter;
    List<Products> productsList;
    int tongtiensp,tongtien,soluongsp = 0;
    int feeship = 30000;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rc_view_gio_hang = view.findViewById(R.id.rc_view_gio_hang);
        tv_tongtiensp = view.findViewById(R.id.tv_tongtiensp);
        tv_phivanchuyen = view.findViewById(R.id.tv_phivanchuyen);
        tv_tongtien = view.findViewById(R.id.tv_tongtien);
        tv_soluong_sp = view.findViewById(R.id.tv_soluong_sp);
        btn_thanhtoan = view.findViewById(R.id.btn_checkout);
        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit_cart);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        getData();
        tv_tongtiensp.setText(tongtiensp+"đ");
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productsList!=null){
                    Intent intent = new Intent(getContext(), CheckOutActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), "Giỏ hàng trống, vui lòng thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private void getData() {
        productsList = new ArrayList<>();
        adapter = new CartAdapter(getActivity(),productsList);
        rc_view_gio_hang.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rc_view_gio_hang.setLayoutManager(linearLayoutManager);

        SharedPreferences sp1 = getActivity().getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String token = sp1.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mofshop.shop/api/cart/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Cart> call = apiService.getCart(token);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful() && response.body()!=null) {
                    progressBar.setVisibility(View.GONE);
                    Cart gioHang = response.body();
                    List<Products> datas = gioHang.getProducts();
                    //dung for de doc array
                    for (Products data : datas) {
                        productsList.add(data);
                        adapter.notifyDataSetChanged();
                    }
                    for (int i =0;i<productsList.size();i++){
                        soluongsp++;
                    }
                    tv_soluong_sp.setText("Mặt hàng ("+soluongsp+" mặt hàng)");
                    tongtiensp = Integer.parseInt(response.body().getTotal());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tv_tongtiensp.setText(decimalFormat.format(tongtiensp)+"đ");
                    tv_phivanchuyen.setText(decimalFormat.format(feeship)+"đ");
                    tongtien = tongtiensp+feeship;
                    tv_tongtien.setText(decimalFormat.format(tongtien)+"đ");
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}