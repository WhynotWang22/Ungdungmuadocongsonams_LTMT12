package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class RecommendHolder extends RecyclerView.ViewHolder {
    RelativeLayout cardview_product_dexuat;
     TextView tv_title_product_dexuat;
     TextView tv_price_product_dexuat;
     ImageView img_product_dexuat;
    public RecommendHolder(@NonNull View itemView) {
        super(itemView);
        cardview_product_dexuat = itemView.findViewById(R.id.cardview_product_dexuat);
        tv_title_product_dexuat = itemView.findViewById(R.id.tv_title_product_dexuat);
        tv_price_product_dexuat = itemView.findViewById(R.id.tv_price_product_dexuat);
        img_product_dexuat = itemView.findViewById(R.id.img_product_dexuat);

    }
}
