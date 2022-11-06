package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class CartHolder extends RecyclerView.ViewHolder {
    ImageView img_product_cart,btn_cong,btn_tru;
    TextView tv_title_product_cart,tv_size_product_cart,tv_color_product_cart,tv_gia_product_cart;
    public CartHolder(@NonNull View itemView) {
        super(itemView);
        img_product_cart = itemView.findViewById(R.id.img_product_cart);
        btn_cong = itemView.findViewById(R.id.btn_cong);
        btn_tru = itemView.findViewById(R.id.btn_tru);
        tv_title_product_cart = itemView.findViewById(R.id.tv_title_product_cart);
        tv_size_product_cart = itemView.findViewById(R.id.tv_size_product_cart);
        tv_color_product_cart = itemView.findViewById(R.id.tv_color_product_cart);
        tv_gia_product_cart = itemView.findViewById(R.id.tv_gia_product_cart);
    }
}
