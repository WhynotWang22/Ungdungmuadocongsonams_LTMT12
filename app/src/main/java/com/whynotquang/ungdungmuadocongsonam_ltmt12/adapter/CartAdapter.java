package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.even.Even;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholoder> {
    int tong = 0;
    int tong2 = 0;
    int tong3 = 0;
    String token;
    SharedPreferences sp;
    List<Products> productsList;
    Context context;
    private int value = 0;

    public void bindData() {
        Intent intent = new Intent("Tongtien");
        intent.putExtra("tongtien", tong);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    public CartAdapter(List<Products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    protected void refreshData(int position) {
        productsList.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.Viewholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new Viewholoder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholoder holder, @SuppressLint("RecyclerView") int position) {
        sp = context.getApplicationContext().getSharedPreferences("Login", MODE_PRIVATE);
        token = sp.getString("token", "");
        Products product = productsList.get(position);
        value = Integer.parseInt(Integer.toString(product.getQuantity()));
        holder.tvTitleCart.setText(product.getTitle());
        holder.tv_values.setText(Integer.toString(product.getQuantity()));
        holder.tvSizeCart.setText("Size:" + product.getSize());
        holder.tvColorCart.setText("| Color:" + product.getColor());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
        holder.tv_price_cart.setText("Giá: " + decimalFormat.format(product.getPrice()) + "đ");
        Glide.with(context).load(product.getProductIMG()).into(holder.img_cart);

        holder.layout_delete_item_cart.setOnClickListener(new View.OnClickListener() {
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
                                .baseUrl(AppConstain.BASE_URL + "cart/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        ApiService apiService = retrofit.create(ApiService.class);
                        Call<Products> call = apiService.deleteCart(token, product._id);
                        call.enqueue(new Callback<Products>() {
                            @Override
                            public void onResponse(Call<Products> call, Response<Products> response) {
                                EventBus.getDefault().postSticky(new Even(1));
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
//                                refreshData(position);
                            }

                            @Override
                            public void onFailure(Call<Products> call, Throwable t) {
                                Toast.makeText(context, "Xóa Thất Bại", Toast.LENGTH_SHORT).show();
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

            }
        });


        holder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstain.BASE_URL + "products/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                Call<Product> call = apiService.getDetailProduct(product.productId);
                call.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.body() != null) {
                            if (response.body().stock > Integer.parseInt(holder.tv_values.getText().toString())) {
                                String currentValue = holder.tv_values.getText().toString();
                                value = Integer.parseInt(currentValue);
                                value++;
                                tong += value * product.getPrice() - (value - 1) * product.getPrice();
                                tong3 = value * product.getPrice();
                                bindData();
                                holder.tv_price_cart.setText("Giá:" + decimalFormat.format(tong3) + "Đ");
                                holder.tv_values.setText(String.valueOf(value));
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(AppConstain.BASE_URL + "cart/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
                                ApiService apiService = retrofit.create(ApiService.class);
                                Call<Products> call1 = apiService.updateCart(token, product._id, value);
                                call1.enqueue(new Callback<Products>() {
                                    @Override
                                    public void onResponse(Call<Products> call, Response<Products> response) {
                                        Toast.makeText(context, "Tăng thành công", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Products> call, Throwable t) {
                                        Toast.makeText(context, "Tăng thất bại ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(context, "Không đủ hàng", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(context, "that bai", Toast.LENGTH_SHORT).show();
                    }
                });

                int sl = (Integer.parseInt(holder.tv_values.getText().toString()));
                if (sl <= 1) {
                    holder.btnminus.setVisibility(View.INVISIBLE);
                } else {
                    holder.btnminus.setVisibility(View.VISIBLE);
                }
            }
        });
        holder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = holder.tv_values.getText().toString();
                value = Integer.parseInt(currentValue);
                value--;
                holder.tv_values.setText(String.valueOf(value));
//                tong = 0;
                tong -= value * product.getPrice();
                tong += (value - 1) * product.getPrice();
                tong2 = value * product.getPrice();
                bindData();
                holder.tv_price_cart.setText("Giá:" + decimalFormat.format(tong2) + "Đ");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstain.BASE_URL + "cart/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                Call<Products> call = apiService.updateCart(token, product._id, value);
                call.enqueue(new Callback<Products>() {
                    @Override
                    public void onResponse(Call<Products> call, Response<Products> response) {
                        Toast.makeText(context, "Giảm thành công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Products> call, Throwable t) {
                        Toast.makeText(context, "Giảm thất bại ", Toast.LENGTH_SHORT).show();
                    }
                });

                int sl = (Integer.parseInt(holder.tv_values.getText().toString()));
                if (sl <= 1) {
                    holder.btnminus.setVisibility(View.INVISIBLE);
                } else {
                    holder.btnminus.setVisibility(View.VISIBLE);
                }

            }
        });
        tong = 0;
        for (int i = 0; i < productsList.size(); i++) {
            tong += productsList.get(i).getPrice() * productsList.get(i).getQuantity();
        }
        bindData();
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (productsList == null) {
            return 0;
        }
        return productsList.size();
    }

    public class Viewholoder extends RecyclerView.ViewHolder {
        ImageView img_cart;
        LinearLayout layout_delete_item_cart;
        TextView tvTitleCart;
        TextView tv_values;
        TextView tvSizeCart;
        TextView tvColorCart;
        TextView tv_price_cart;
        Button btnminus;
        Button btnplus;

        public Viewholoder(@NonNull View itemView) {
            super(itemView);
            Viewholoder viewholoder = null;
            img_cart = (ImageView) itemView.findViewById(R.id.img_cart);
            layout_delete_item_cart = (LinearLayout) itemView.findViewById(R.id.layout_delete_item_cart);
            tvTitleCart = (TextView) itemView.findViewById(R.id.tv_title_cart);
            tvSizeCart = (TextView) itemView.findViewById(R.id.tv_size_cart);
            tvColorCart = (TextView) itemView.findViewById(R.id.tv_color_cart);
            tv_price_cart = (TextView) itemView.findViewById(R.id.tv_price_cart);
            btnminus = (Button) itemView.findViewById(R.id.btnminus);
            tv_values = (TextView) itemView.findViewById(R.id.tv_values);
            btnplus = (Button) itemView.findViewById(R.id.btnplus);


        }

    }
}
