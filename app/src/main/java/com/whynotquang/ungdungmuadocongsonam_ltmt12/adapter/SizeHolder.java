package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class SizeHolder extends RecyclerView.ViewHolder {
    View itemView;
    RadioButton radio_size;

    public SizeHolder(@NonNull View itemView) {
        super(itemView);
        radio_size = itemView.findViewById(R.id.radio_size);
        this.itemView = itemView;
    }
}
