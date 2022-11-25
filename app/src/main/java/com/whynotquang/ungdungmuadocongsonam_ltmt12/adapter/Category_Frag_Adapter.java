package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ProductCategoryActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Category;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.util.List;

public class Category_Frag_Adapter extends RecyclerView.Adapter<Category_Frag_Adapter.viewholder> {

    List<Category> categoryList;
    Context context;

    public Category_Frag_Adapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public Category_Frag_Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_frag_categorys,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Category_Frag_Adapter.viewholder holder, int position) {
      Category category = categoryList.get(position);
      holder.tvTitleCategoryFrag.setText(category.getTitle());
      Glide.with(context).load(category.getCateImg()).placeholder(R.drawable.full)
              .error(R.drawable.full)
              .into(holder.imageCategoryFrag);
        holder.cardviewFragCategorys.setOnClickListener(new View.OnClickListener() {
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
        private LinearLayout cardviewFragCategorys;
        private ImageView imageCategoryFrag;
        private TextView tvTitleCategoryFrag;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            cardviewFragCategorys = (LinearLayout) itemView.findViewById(R.id.cardview_frag_categorys);
            imageCategoryFrag = (ImageView) itemView.findViewById(R.id.image_category_frag);
            tvTitleCategoryFrag = (TextView) itemView.findViewById(R.id.tv_title_category_frag);
        }
    }
}
