package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.CheckOutActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CartAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.even.Even;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CartFragment extends Fragment {

    RecyclerView rc_cart;
    private CartAdapter cartAdapter;
    List<Products> productList;
    private TextView tv_tongtien;
    private TextView tv_soluong_cart;
    private Button btnCheckoutCart, btnvalues;
    private ProgressBar progressBar;
    private LinearLayout layout_thanhtoan;
    LinearLayout layout_not_cart;
    int tongtien_cart = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rc_cart = view.findViewById(R.id.rc_view_gio_hang);
        tv_tongtien = (TextView) view.findViewById(R.id.tv_tongtien);
        tv_soluong_cart = (TextView) view.findViewById(R.id.tv_soluong_cart);
        btnCheckoutCart = (Button) view.findViewById(R.id.btn_checkout);
        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit_cart);
        layout_thanhtoan = (LinearLayout) view.findViewById(R.id.layout_thanhtoan);
        layout_not_cart = view.findViewById(R.id.layout_not_cart);

        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);
        progressBar.setVisibility(View.GONE);
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("Tongtien"));
        getdataCart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver, new IntentFilter("Tongtien"));

        btnCheckoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostCartCheckout();
            }
        });
        layout_not_cart.setVisibility(View.GONE);
        layout_thanhtoan.setVisibility(View.GONE);

        return view;
    }


    private void getdataCart() {
        SharedPreferences sp = getContext().getSharedPreferences("Login", MODE_PRIVATE);
        String token = sp.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "cart/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Cart> call = apiService.getlistCart(token);
        call.enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ///set recyclerview
                    productList = new ArrayList<>();
                    CartAdapter cartAdapter = new CartAdapter(productList, getContext());
                    cartAdapter.notifyDataSetChanged();
                    rc_cart.setAdapter(cartAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rc_cart.setLayoutManager(linearLayoutManager);
                    productList.addAll(response.body().getProducts());
                    tv_soluong_cart.setText(productList.size() + " sản phẩm");
                    if (productList == null || productList.size() == 0) {
                        layout_thanhtoan.setVisibility(View.GONE);
                        layout_not_cart.setVisibility(View.VISIBLE);
                    } else {
                        layout_thanhtoan.setVisibility(View.VISIBLE);
                        layout_not_cart.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {
                layout_thanhtoan.setVisibility(View.GONE);
                layout_not_cart.setVisibility(View.VISIBLE);
//                Toast.makeText(getActivity(), "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            int tongtien = intent.getIntExtra("tongtien", 0);
            Log.d("msg", "onReceive: " + tongtien);
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
            tv_tongtien.setText(decimalFormat.format(tongtien) + " đ");
            tongtien_cart = tongtien;
        }
    };

    private void PostCartCheckout() {
        if (tongtien_cart < 20000) {
            Toast.makeText(getActivity(), "Vui lòng đặt đơn hàng trên 20.000đ.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(getActivity(), CheckOutActivity.class);
        startActivity(intent);
    }

    ///tai lai du lieu
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Even event) {
        if (event.getId() == 1) {
            getdataCart();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}


