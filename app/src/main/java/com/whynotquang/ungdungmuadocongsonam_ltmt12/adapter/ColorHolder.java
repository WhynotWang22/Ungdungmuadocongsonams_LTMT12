package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;

public class ColorHolder extends RecyclerView.ViewHolder {
    View itemView;
    RadioButton radio_color;

    public ColorHolder(@NonNull View itemView) {
        super(itemView);
        radio_color = itemView.findViewById(R.id.radio_color);
        this.itemView = itemView;
    }
}
