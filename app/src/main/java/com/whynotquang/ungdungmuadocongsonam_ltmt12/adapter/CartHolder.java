package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class CartHolder extends RecyclerView.ViewHolder {
    SwipeRevealLayout item_cart;
    RelativeLayout layout_delete;
    ImageView img_product_cart,btn_cong,btn_tru;
    TextView tv_title_product_cart,tv_size_product_cart,tv_color_product_cart,tv_gia_product_cart,tv_soluong_product;
    public CartHolder(@NonNull View itemView) {
        super(itemView);
        img_product_cart = itemView.findViewById(R.id.img_product_cart);
        btn_cong = itemView.findViewById(R.id.btn_cong);
        btn_tru = itemView.findViewById(R.id.btn_tru);
        tv_title_product_cart = itemView.findViewById(R.id.tv_title_product_cart);
        tv_size_product_cart = itemView.findViewById(R.id.tv_size_product_cart);
        tv_color_product_cart = itemView.findViewById(R.id.tv_color_product_cart);
        tv_gia_product_cart = itemView.findViewById(R.id.tv_gia_product_cart);
        tv_soluong_product = itemView.findViewById(R.id.tv_soluong_product);
        item_cart = itemView.findViewById(R.id.item_cart);
        layout_delete = itemView.findViewById(R.id.layout_delete);

    }
}
