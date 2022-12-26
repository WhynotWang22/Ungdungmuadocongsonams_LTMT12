package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.interFace.ItemClickListener;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorHolder> {
    ArrayList<String> list;
    int selectedPosition = -1;
    ItemClickListener itemClickListener;

    public ColorAdapter(ArrayList<String> list, ItemClickListener itemClickListener) {
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color,parent,false);
        return new ColorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorHolder holder, @SuppressLint("RecyclerView") int position) {
        if (list.get(position).equalsIgnoreCase("white")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_white);
        }else if (list.get(position).equalsIgnoreCase("black")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_black);
        } else if (list.get(position).equalsIgnoreCase("Gray")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_xam);
        }else if (list.get(position).equalsIgnoreCase("Blue")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_blue);
        }else if (list.get(position).equalsIgnoreCase("Green")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_green);
        }else if (list.get(position).equalsIgnoreCase("Yellow")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_yellow);
        }else if (list.get(position).equalsIgnoreCase("Orange")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_orange);
        }else if (list.get(position).equalsIgnoreCase("Pink")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_pink);
        }else if (list.get(position).equalsIgnoreCase("Red")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_red);
        }else if (list.get(position).equalsIgnoreCase("lightblue")){
            holder.radio_color.setBackgroundResource(R.drawable.bg_color_lightblue);
        }
        holder.radio_color.setChecked(position == selectedPosition);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, list.size());
                itemClickListener.onClickColor(list.get(position));
            }
        };
        holder.radio_color.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
