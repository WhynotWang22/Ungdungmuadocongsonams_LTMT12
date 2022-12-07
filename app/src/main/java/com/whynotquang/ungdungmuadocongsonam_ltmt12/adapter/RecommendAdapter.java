package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ChitietActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendHolder> {
     Context context;
     List<Product> productList;


    public RecommendAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public RecommendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_dexuat,parent,false);
        return new RecommendHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = productList.get(position);
        holder.tv_title_product_dexuat.setText(product.getTitle());
        holder.tv_product_daban.setText("Đã Bán" + product.getSold() + "");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_price_product_dexuat.setText(decimalFormat.format(product.getPrice())+ "đ");
        Glide.with(context).load(product.getImg())
                .into(holder.img_product_dexuat);
        holder.tv_title_product_dexuat.setMaxLines(2);
        holder.tv_title_product_dexuat.setEllipsize(TextUtils.TruncateAt.END);

        holder.cardview_product_dexuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChitietActivity.class);
                intent.putExtra("id",productList.get(position).get_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
