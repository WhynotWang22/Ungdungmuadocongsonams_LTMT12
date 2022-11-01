package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class NotificationHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView tv_id_noti,tv_time_noti,tv_title_noti,tv_desc_noti;
    LinearLayout liner_noti;
    public NotificationHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.img_noti);
        tv_id_noti = itemView.findViewById(R.id.tv_id_noti);
        tv_time_noti = itemView.findViewById(R.id.tv_time_noti);
        tv_title_noti = itemView.findViewById(R.id.tv_title_noti);
        tv_desc_noti = itemView.findViewById(R.id.tv_desc_noti);
        liner_noti = itemView.findViewById(R.id.liner_noti);

    }
}
