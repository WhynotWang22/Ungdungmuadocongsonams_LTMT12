package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class ProductHolder extends RecyclerView.ViewHolder {
    RelativeLayout cardview_product;
     TextView tv_title_product;
     TextView tv_price_product;
     ImageView img_product;
    public ProductHolder(@NonNull View itemView) {
        super(itemView);
        cardview_product = itemView.findViewById(R.id.cardview_product);
        tv_title_product = itemView.findViewById(R.id.tv_title_product);
        tv_price_product = itemView.findViewById(R.id.tv_price_product);
        img_product = itemView.findViewById(R.id.img_product);

    }
}
