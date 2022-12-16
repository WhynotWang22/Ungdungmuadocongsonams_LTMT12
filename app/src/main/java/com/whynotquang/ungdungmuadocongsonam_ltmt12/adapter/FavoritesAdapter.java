package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ChitietActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Favourites;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.Viewholoder> {
    Context context;
    List<Favourites> productsList;

    public FavoritesAdapter(Context context, List<Favourites> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public FavoritesAdapter.Viewholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yeuthich, parent, false);
        return new Viewholoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.Viewholoder holder, @SuppressLint("RecyclerView") int position) {
        Favourites favourites = productsList.get(position);
        if (favourites != null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstain.BASE_URL + "products/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiService apiService = retrofit.create(ApiService.class);
            apiService.getDetailProduct(favourites.getProductId()).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    holder.tvTitleProductYeuthich.setText(response.body().getTitle());
                    holder.tv_favorites_daban.setText("Đã bán "+ response.body().getSold() + "");
                    holder.tvPriceProductYeuthich.setText(new DecimalFormat("###,###,###,###").format(response.body().getPrice()) + "đ");
                    Glide.with(context).load(response.body().getImg()).into(holder.imgProductYeuthich);
                    holder.imgProductYeuthich.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, ChitietActivity.class);
                            intent.putExtra("id", productsList.get(position).getProductId());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    });
                }
                @Override
                public void onFailure(Call<Product> call, Throwable t) {

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        if (productsList != null) {
            return productsList.size();
        }
        return 0;
    }
    public class Viewholoder extends RecyclerView.ViewHolder {
        private ImageView imgProductYeuthich;
        private TextView tvTitleProductYeuthich;
        private RelativeLayout layoutPriceCategory;
        private TextView tvPriceProductYeuthich;
        private TextView tv_favorites_daban;

        public Viewholoder(@NonNull View itemView) {
            super(itemView);
            imgProductYeuthich = (ImageView) itemView.findViewById(R.id.img_product_yeuthich);
            tvTitleProductYeuthich = (TextView) itemView.findViewById(R.id.tv_title_product_yeuthich);
            layoutPriceCategory = (RelativeLayout) itemView.findViewById(R.id.layout_price_category);
            tvPriceProductYeuthich = (TextView) itemView.findViewById(R.id.tv_price_product_yeuthich);
            tv_favorites_daban = (TextView) itemView.findViewById(R.id.tv_favorites_daban);
        }
    }
}
