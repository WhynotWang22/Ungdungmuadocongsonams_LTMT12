package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ChitietActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.even.Even;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ProductAddCart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.Viewholoder> {
    Context context;
    List<Products> productsList;
    public FavoritesAdapter(Context context, List<Products> productsList) {
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
        Products products = productsList.get(position);
        holder.tvTitleProductYeuthich.setText(products.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
        holder.tvPriceProductYeuthich.setText("Giá:" + decimalFormat.format(products.getPrice()) + "vnđ");
        Glide.with(context).load(products.getProductIMG()).into(holder.imgProductYeuthich);
        holder.imgProductYeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChitietActivity.class);
                intent.putExtra("id", productsList.get(position).getProductId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.layoutyeuthichDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp1 = context.getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
                String token = sp1.getString("token", "");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstain.BASE_URL + "favorite/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                Call<Products> call = apiService.deleteItemFavorite(token, products.get_id());
                Log.d("eeeee", "eeeeeeeee" + products.get_id());
                call.enqueue(new Callback<Products>() {
                    @Override
                    public void onResponse(Call<Products> call, Response<Products> response) {
                        EventBus.getDefault().postSticky(new Even(1));
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Products> call, Throwable t) {
                        Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    @Override
    public int getItemCount() {
        if (productsList != null) {
            return productsList.size();
        }
        return 0;
    }


    public class Viewholoder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout rlYeuthich;
        private RelativeLayout layoutyeuthichDelete;
        private ImageView imgProductYeuthich;
        private TextView tvTitleProductYeuthich;
        private LinearLayout layoutPriceCategory;
        private TextView tvPriceProductYeuthich;

        public Viewholoder(@NonNull View itemView) {
            super(itemView);
            layoutyeuthichDelete = (RelativeLayout) itemView.findViewById(R.id.layoutyeuthich_delete);
            imgProductYeuthich = (ImageView) itemView.findViewById(R.id.img_product_yeuthich);
            tvTitleProductYeuthich = (TextView) itemView.findViewById(R.id.tv_title_product_yeuthich);
            layoutPriceCategory = (LinearLayout) itemView.findViewById(R.id.layout_price_category);
            tvPriceProductYeuthich = (TextView) itemView.findViewById(R.id.tv_price_product_yeuthich);
        }
    }

}
