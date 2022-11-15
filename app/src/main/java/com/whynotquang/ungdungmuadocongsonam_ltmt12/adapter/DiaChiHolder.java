package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class DiaChiHolder extends RecyclerView.ViewHolder {
    TextView tv_name_diachi,tv_diachi_diachi,tv_sdt_diachi;
    CardView cardView;
    RadioButton radioButton;
    public DiaChiHolder(@NonNull View itemView) {
        super(itemView);
        tv_name_diachi = itemView.findViewById(R.id.tv_name_diachi);
        tv_diachi_diachi = itemView.findViewById(R.id.tv_diachi_diachi);
        tv_sdt_diachi = itemView.findViewById(R.id.tv_sdt_diachi);
        radioButton = itemView.findViewById(R.id.radio_address);
        cardView = itemView.findViewById(R.id.cardview_diachi);

    }
}
