package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ProductCategoryActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.SendemailActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Category;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {

    Context context;
    List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, int position) {
        Category category = categoryList.get(position);
        holder.tv_title_category.setText(category.getTitle());
        Glide.with(context).load(category.getCateImg()).into(holder.img_category);
        holder.cardview_productbycategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductCategoryActivity.class);
                intent.putExtra("id",category.getId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView tv_title_category;
        CircleImageView img_category;
        RelativeLayout cardview_productbycategory;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_title_category = itemView.findViewById(R.id.tv_title_category);
            img_category = itemView.findViewById(R.id.img_category);
            cardview_productbycategory = itemView.findViewById(R.id.cardview_productcatgory);
        }
    }
}
