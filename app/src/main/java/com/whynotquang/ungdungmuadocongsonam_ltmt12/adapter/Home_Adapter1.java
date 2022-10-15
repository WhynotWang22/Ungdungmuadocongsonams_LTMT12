package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.HomeDanhMuc;

import java.util.ArrayList;

public class Home_Adapter1 extends RecyclerView.Adapter<Home_Adapter1.Viewholoder> {
    private Context mContext;
    ArrayList<HomeDanhMuc> mHomeDanhmuc;

    public Home_Adapter1(Context mContext, ArrayList<HomeDanhMuc> mHomeDanhmuc) {
        this.mContext = mContext;
        this.mHomeDanhmuc = mHomeDanhmuc;
    }

    @NonNull
    @Override
    public Home_Adapter1.Viewholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyc_home,parent,false);
        return new Viewholoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Home_Adapter1.Viewholoder holder, int position) {
      HomeDanhMuc homeDanhMuc = mHomeDanhmuc.get(position);
//      holder.txt_sp1.setText(position);
//      holder.spHome_Image.setImageResource(homeDanhMuc.getAnhsp());
//      holder.txt_sp1.setText(homeDanhMuc.getTen());
    }

    @Override
    public int getItemCount() {
        return mHomeDanhmuc.size();
    }

    public class Viewholoder extends RecyclerView.ViewHolder {
        ImageView spHome_Image;
        TextView txt_sp1;
        public Viewholoder(@NonNull View itemView) {
            super(itemView);
            spHome_Image = itemView.findViewById(R.id.image_sp1);
            txt_sp1 = itemView.findViewById(R.id.txt_sp1);
        }
    }
}
