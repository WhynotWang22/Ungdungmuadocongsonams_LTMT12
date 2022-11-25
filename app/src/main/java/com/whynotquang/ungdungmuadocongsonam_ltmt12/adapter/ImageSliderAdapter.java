package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ImageProductActivity;

import java.util.ArrayList;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.MyHolder> {
    Context context;
    ArrayList<String> list;

    public ImageSliderAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img_slider, null);
        return new MyHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyHolder viewHolder, int position) {
        Glide.with(context).load(list.get(position)).into(viewHolder.imageView);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageProductActivity.class);
                intent.putExtra("url",list.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class MyHolder extends ViewHolder {
        View view;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_slider);
            this.view = itemView;
        }
    }
}
