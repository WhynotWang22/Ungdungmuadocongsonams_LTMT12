package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ChitietActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.viewholder> {
    Context context;
    List<Product> productList;

    public ProductCategoryAdapter(Context context,List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_productscategory, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        Product product = productList.get(position);
        holder.tvTitleProductcategory.setText(product.getTitle());
        holder.tv_productcatagory_daban.setText("Đã Bán"+ product.getSold() + "");
        holder.tvTitleProductcategory.setMaxLines(2);
        holder.tvTitleProductcategory.setEllipsize(TextUtils.TruncateAt.END);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvPriceProductcategory.setText(decimalFormat.format(product.getPrice()) + "đ");
        Glide.with(context).load(product.getImg()).into(holder.imgProductcategory);
        holder.card_ItemClickDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChitietActivity.class);
                intent.putExtra("id", product.get_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        private ImageView imgProductcategory;
        private TextView tvTitleProductcategory;
        private TextView tvPriceProductcategory;
        private TextView tv_productcatagory_daban;
        private RelativeLayout card_ItemClickDetail;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            imgProductcategory = (ImageView) itemView.findViewById(R.id.img_productcategory);
            tvTitleProductcategory = (TextView) itemView.findViewById(R.id.tv_title_productcategory);
            tvPriceProductcategory = (TextView) itemView.findViewById(R.id.tv_price_productcategory);
            tv_productcatagory_daban = (TextView) itemView.findViewById(R.id.tv_productcatagory_daban);
            card_ItemClickDetail = (RelativeLayout) itemView.findViewById(R.id.card_ItemClickDetail);
        }
    }
}
