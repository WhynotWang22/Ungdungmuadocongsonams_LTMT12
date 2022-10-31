package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ImageProductActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ItemClickListener;

import java.util.ArrayList;

public class SizeAdapter extends RecyclerView.Adapter<SizeHolder> {
    ArrayList<String> list;
    int selectedPosition = -1;
    ItemClickListener itemClickListener;

    public SizeAdapter(ArrayList<String> list, ItemClickListener itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SizeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_size,parent,false);
        return new SizeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeHolder holder, int position) {
        holder.radio_size.setText(list.get(position));
        holder.radio_size.setChecked(position == selectedPosition);
        holder.radio_size.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    selectedPosition = holder.getAdapterPosition();
                    itemClickListener.onClickSize(holder.radio_size.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
