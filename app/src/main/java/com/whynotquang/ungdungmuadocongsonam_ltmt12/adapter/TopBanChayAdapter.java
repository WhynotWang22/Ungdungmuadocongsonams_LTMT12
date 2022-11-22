package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.text.DecimalFormat;
import java.util.List;

public class TopBanChayAdapter extends RecyclerView.Adapter<TopBanChayAdapter.viewholoder> {
    List<Product> productList;
    Context context;

    public TopBanChayAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_aopolo,parent,false);
        return new viewholoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholoder holder, int position) {
      Product product = productList.get(position);
      holder.tvTitleProductAopolo.setText(product.getTitle());
      DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
      holder.tvPriceProductAopolo.setText("Giá:" +decimalFormat.format(product.getPrice())+ "Đ");
      Glide.with(context).load(product.getImg()).error(R.drawable.full)
              .placeholder(R.drawable.full)
              .into(holder.imgProductAopolo);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class viewholoder extends RecyclerView.ViewHolder {
        private RelativeLayout cardviewAopolo;
        private ImageView imgProductAopolo;
        private TextView tvTitleProductAopolo;
        private TextView tvPriceProductAopolo;



        public viewholoder(@NonNull View itemView) {
            super(itemView);
            cardviewAopolo = (RelativeLayout) itemView.findViewById(R.id.cardview_aopolo);
            imgProductAopolo = (ImageView) itemView.findViewById(R.id.img_product_Aopolo);
            tvTitleProductAopolo = (TextView) itemView.findViewById(R.id.tv_title_product_Aopolo);
            tvPriceProductAopolo = (TextView) itemView.findViewById(R.id.tv_price_product_Aopolo);
        }
    }
}
