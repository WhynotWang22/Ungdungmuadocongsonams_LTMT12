package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ChitietActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.OrderDetailActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {
    Context context;
    List<Order> orderList;
    List<Products> productsList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, @SuppressLint("RecyclerView") int position) {
        Order order = orderList.get(position);
        holder.tv_title_order.setText(order.getProducts().get(0).getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_thanhtien_order.setText(decimalFormat.format(order.getTotal()) + "đ");
        holder.tv_trangthai_order.setText(order.getStatus());
        Glide.with(context).load(order.getProducts().get(0).getProductIMG()).into(holder.img_sanpham_order);
        int soluong = 0;
        for (int i = 0; i < order.getProducts().size(); i++) {
            soluong++;
        }
        holder.tv_soluong_order.setText(String.valueOf(soluong)+" sản phẩm");
        holder.tv_size_color_order.setText("Size: "+order.getProducts().get(0).getSize()+" | Color: "+order.getProducts().get(0).getColor());

        if (order.getStatus().equalsIgnoreCase("Đang chờ xác nhận")) {
            holder.tv_trangthai_order.setTextColor(Color.parseColor("#E49542"));
        } else if (order.getStatus().equalsIgnoreCase("Đang chuẩn bị hàng")) {
            holder.tv_trangthai_order.setTextColor(Color.parseColor("#E49542"));
        } else if (order.getStatus().equalsIgnoreCase("Đang giao hàng")) {
            holder.tv_trangthai_order.setTextColor(Color.parseColor("#E49542"));
        } else if (order.getStatus().equalsIgnoreCase("Giao hàng thành công")) {
            holder.tv_trangthai_order.setTextColor(Color.parseColor("#4CAF50"));
        }else if (order.getStatus().equalsIgnoreCase("Người dùng đã hủy đơn hàng")) {
            holder.tv_trangthai_order.setTextColor(Color.RED);
        }
//        holder.tv_title_order.setText(order.get_id());
//        holder.tv_title_order.setText(order.get_id());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("id", order.get_id());
                context.startActivity(intent);
            }
        });
        holder.tv_trangthai_order.setMaxLines(1);
        holder.tv_trangthai_order.setEllipsize(TextUtils.TruncateAt.END);
        holder.tv_title_order.setMaxLines(1);
        holder.tv_title_order.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}