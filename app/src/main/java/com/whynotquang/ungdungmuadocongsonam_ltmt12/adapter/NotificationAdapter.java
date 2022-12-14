package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationHolder> {
    Context context;
    List<Notification> list;

    public NotificationAdapter(Context context, List<Notification> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification,parent,false);
        return new NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        Notification notification = list.get(position);
        holder.tv_title_noti.setText(notification.getTitle());
        holder.tv_desc_noti.setText(notification.getDesc());
        holder.tv_time_noti.setText(notification.getTime());
        Glide.with(context).load(notification.getImages()).into(holder.imageView);
        holder.tv_title_noti.setMaxLines(1);
        holder.tv_title_noti.setEllipsize(TextUtils.TruncateAt.END);
        holder.tv_desc_noti.setMaxLines(3);
        holder.tv_desc_noti.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
