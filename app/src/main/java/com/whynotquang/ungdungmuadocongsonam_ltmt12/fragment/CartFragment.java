package com.whynotquang.ungdungmuadocongsonam_ltmt12.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ProductAddCart;
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
    int soluongsanpham = 0;
    String id;
    RecyclerView rc_cart;
    private CartAdapter cartAdapter;
    List<Products> productList;
    private TextView tv_tongtien;
    private TextView tv_tongtiensp;
    private Button btnCheckoutCart, btnvalues;
    private ProgressBar progressBar;
    private LinearLayout lout_test;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rc_cart = view.findViewById(R.id.rc_view_gio_hang);
        tv_tongtien = (TextView) view.findViewById(R.id.tv_tongtien);
        btnCheckoutCart = (Button) view.findViewById(R.id.btn_checkout);
        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit_cart);
        lout_test = (LinearLayout) view.findViewById(R.id.lout_test);
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
        Call<ProductAddCart> call = apiService.getlistCart(token);
        call.enqueue(new Callback<ProductAddCart>() {
            @Override
            public void onResponse(Call<ProductAddCart> call, Response<ProductAddCart> response) {
                if (response.body() != null) {
                    int tong=0;
                    ///set recyclerview
                    productList = new ArrayList<>();
                    CartAdapter cartAdapter = new CartAdapter(productList, getContext());
                    cartAdapter.notifyDataSetChanged();
                    rc_cart.setAdapter(cartAdapter);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rc_cart.setLayoutManager(linearLayoutManager);
                    //add item
                    productList.addAll(response.body().getProducts());
                    ///lay so luong sp trong gio hang

                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
//                    tv_tongtiensp.setText(cartAdapter.getItemCount() + "sản phẩm)");
//                    tv_tongtien.setText(decimalFormat.format(response.body().getTotal()) + "đ");

                } else {
                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ProductAddCart> call, Throwable t) {
                Toast.makeText(getContext(), "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceive(Context context, Intent intent) {
            int tongtien = intent.getIntExtra("tongtien", 0);
            Log.d("msg", "onReceive: " + tongtien);
            tv_tongtien.setText((tongtien + "đ"));
        }
    };

    private void PostCartCheckout() {
        Intent i = new Intent(getActivity(), CheckOutActivity.class);
//       i.putExtra("key", tv_tongtien.getText().toString());
        startActivity(i);
    }
}


