package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class OrderHolder extends RecyclerView.ViewHolder {
    ImageView img_sanpham_order;
    RelativeLayout layout;
    TextView tv_title_order,tv_size_color_order,tv_soluong_order,tv_trangthai_order,tv_thanhtien_order;
    public OrderHolder(@NonNull View itemView) {
        super(itemView);
        img_sanpham_order = itemView.findViewById(R.id.img_sanpham_order);
        layout = itemView.findViewById(R.id.layout_item_order);
        tv_title_order = itemView.findViewById(R.id.tv_title_order);
        tv_size_color_order = itemView.findViewById(R.id.tv_size_color_order);
        tv_soluong_order = itemView.findViewById(R.id.tv_soluong_order);
        tv_trangthai_order = itemView.findViewById(R.id.tv_trangthai_order);
        tv_thanhtien_order = itemView.findViewById(R.id.tv_thanhtien_order);

    }
}
