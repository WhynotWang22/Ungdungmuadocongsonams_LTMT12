package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Cart;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> {
    Context context;
    List<Products> productsList;
    int soluong = 0;

    public CartAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_cart,parent,false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, @SuppressLint("RecyclerView") int position) {
        Products products = productsList.get(position);
        holder.tv_title_product_cart.setText(products.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_gia_product_cart.setText(decimalFormat.format(products.getPrice())+"đ");
        holder.tv_size_product_cart.setText("Size: "+products.getSize()+" | ");
        holder.tv_color_product_cart.setText("Màu: "+products.getColor());
        holder.tv_soluong_product.setText(String.valueOf(products.getQuantity()));
        Glide.with(holder.img_product_cart).load(products.getProductIMG()).into(holder.img_product_cart);
        holder.layout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences sp1 = context.getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
                        String token = sp1.getString("token", "");
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("https://mofshop.shop/api/cart/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiService apiService = retrofit.create(ApiService.class);
                        String id = productsList.get(position).get_id();
                        Call<Products> call = apiService.deleteCart(token,id);
                        call.enqueue(new Callback<Products>() {
                            @Override
                            public void onResponse(Call<Products> call, Response<Products> response) {
                                if (response.isSuccessful()){
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }else {
                                    Toast.makeText(context, "Xóa ko thành công", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Products> call, Throwable t) {
                                Toast.makeText(context, "Error api delete", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                notifyDataSetChanged();
            }
        });
        holder.btn_cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (soluong< 10){
                    soluong = productsList.get(position).quantity++;
                    holder.tv_soluong_product.setText(String.valueOf(soluong));
                }

                SharedPreferences sp1 = context.getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
                String token = sp1.getString("token", "");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://mofshop.shop/api/cart/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                String id = productsList.get(position).get_id();
                Call<Products> call = apiService.updateCart(token,id,soluong);
                call.enqueue(new Callback<Products>() {
                    @Override
                    public void onResponse(Call<Products> call, Response<Products> response) {

                    }

                    @Override
                    public void onFailure(Call<Products> call, Throwable t) {
                        Toast.makeText(context, "Error api update", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.btn_tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (soluong>1){
                    soluong = productsList.get(position).quantity--;
                    holder.tv_soluong_product.setText(String.valueOf(soluong));
                }

                SharedPreferences sp1 = context.getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
                String token = sp1.getString("token", "");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://mofshop.shop/api/cart/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                String id = productsList.get(position).get_id();
                Call<Products> call = apiService.updateCart(token,id,soluong);
                call.enqueue(new Callback<Products>() {
                    @Override
                    public void onResponse(Call<Products> call, Response<Products> response) {

                    }

                    @Override
                    public void onFailure(Call<Products> call, Throwable t) {
                        Toast.makeText(context, "Error api update", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }
}
