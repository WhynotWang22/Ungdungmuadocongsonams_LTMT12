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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.CheckOutActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter.CartAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
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
    double giatien = 0;
    String id;
    RecyclerView rc_cart;
    private CartAdapter cartAdapter;
    List<Products> productList;
    private ImageView iconNoCart;
    private TextView titleNoCart;
    private TextView tv_total_product_cart;
    private TextView tvPriceDeliveryCart;
    private TextView tvTotalCart;
    private Button btnCheckoutCart, btnvalues;
    private LinearLayout layoutCart;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rc_cart = view.findViewById(R.id.rc_view_gio_hang);
//        iconNoCart = (ImageView) view.findViewById(R.id.iconNoCart);
//        titleNoCart = (TextView) view.findViewById(R.id.titleNoCart);
        ///
//        tv_total_product_cart = (TextView) view.findViewById(R.id.tv_tongtien);
        tvTotalCart = (TextView) view.findViewById(R.id.tv_tongtien);
        btnCheckoutCart = (Button) view.findViewById(R.id.btn_checkout);
//        layoutCart = (LinearLayout) view.findViewById(R.id.layout_cart);
//        progressBar = (ProgressBar) view.findViewById(R.id.spin_kit_cart);
        Sprite threeBounce = new ThreeBounce();
//        progressBar.setIndeterminateDrawable(threeBounce);
//        progressBar.setVisibility(View.GONE);

        Intent intent = getActivity().getIntent();
        id = intent.getStringExtra("id");

        getdataCart();


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
                    ///set recyclerview
                    productList = new ArrayList<>();
                    CartAdapter cartAdapter = new CartAdapter(productList, getContext());
                    cartAdapter.notifyDataSetChanged();
                    rc_cart.setAdapter(cartAdapter);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    rc_cart.setLayoutManager(linearLayoutManager);
                    //add item
                    productList.addAll(response.body().getProducts());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
//                    tv_total_product_cart.setText(decimalFormat.format(response.body().getTotal()) + "đ");
                    tvTotalCart.setText(decimalFormat.format(giatien) + "đ");
                    Log.d("eee", "eee" + giatien);
//                    iconNoCart.setVisibility(View.GONE);
//                    titleNoCart.setVisibility(View.GONE);



                } else {
                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
//                    iconNoCart.setVisibility(View.VISIBLE);
//                    titleNoCart.setVisibility(View.VISIBLE);
//                    layoutCart.setVisibility(View.GONE);
                }
                rc_cart.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductAddCart> call, Throwable t) {
                Toast.makeText(getContext(), "Giỏ hàng đang trống", Toast.LENGTH_SHORT).show();
//                layoutCart.setVisibility(View.GONE);

            }
        });

    }

    private void PostCartCheckout() {
        Intent i = new Intent(getActivity(), CheckOutActivity.class);
        i.putExtra("key", tvTotalCart.getText().toString());
        startActivity(i);
    }


}

