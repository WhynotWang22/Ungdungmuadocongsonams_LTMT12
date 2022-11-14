package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class AddressHolder extends RecyclerView.ViewHolder {
    TextView tv_name_address,tv_sdt_address,tv_diachi_address;
    public AddressHolder(@NonNull View itemView) {
        super(itemView);
        tv_name_address = itemView.findViewById(R.id.tv_name_address);
        tv_sdt_address = itemView.findViewById(R.id.tv_sdt_address);
        tv_diachi_address = itemView.findViewById(R.id.tv_diachi_address);

    }
}
