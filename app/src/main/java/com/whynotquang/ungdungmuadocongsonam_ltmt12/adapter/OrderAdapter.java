package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {
    Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tv_title_order.setText(order.get_id());
        holder.tv_thanhtien_order.setText(String.valueOf(order.getTotal()));
        holder.tv_trangthai_order.setText(order.getStatus());
//        holder.tv_title_order.setText(order.get_id());
//        holder.tv_title_order.setText(order.get_id());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
