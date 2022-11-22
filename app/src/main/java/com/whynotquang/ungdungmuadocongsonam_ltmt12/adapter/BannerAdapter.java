package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Banner;

import java.util.List;

public class BannerAdapter extends SliderViewAdapter<BannerAdapter.Holder> {
    final List<Banner> bannerList;
    final Context context;

    public BannerAdapter(List<Banner> bannerList, Context context) {
        this.bannerList = bannerList;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        Banner banner = bannerList.get(position);
        Glide.with(viewHolder.imageView).load(banner.getAnh()).placeholder(R.drawable.full)
                .error(R.drawable.full)
                .into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }


    public class Holder extends SliderViewAdapter.ViewHolder{
        final ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_banner);
        }
    }

}
