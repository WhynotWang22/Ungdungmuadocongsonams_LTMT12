package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ChitietActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.OrderDetailActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ReviewActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Order;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.Viewholoder> {

    List<Products> productsList;
    Context context;
    String id;

    public ProductOrderAdapter(List<Products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductOrderAdapter.Viewholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_orderdetail, parent, false);
        return new Viewholoder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderAdapter.Viewholoder holder, @SuppressLint("RecyclerView") int position) {
        Products product = productsList.get(position);
        holder.tv_title_product_detail_order.setText(product.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tv_thanhtien_product_detail_order.setText(decimalFormat.format(product.getPrice() * product.getQuantity()) + "??");
        holder.tv_sizecolor_product_detail.setText("Size: " + product.getSize() + " | M??u: " + product.getColor());
        holder.tv_soluong_product_detail_order.setText("S??? l?????ng "+ String.valueOf(product.getQuantity()));
        Glide.with(context).load(product.getProductIMG()).into(holder.img_product_detail_order);
        holder.tv_danhgiasp.setVisibility(View.GONE);
        holder.layout_item_order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChitietActivity.class);
                intent.putExtra("id", productsList.get(position).getProductId());
                context.startActivity(intent);
            }
        });
//        holder.tv_danhgiasp.setVisibility(View.GONE);
        //phan danh gia
        Intent intent = ((Activity) context).getIntent();
        id = intent.getStringExtra("id");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstain.BASE_URL + "order/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<Order> call = apiService.getDetailOrder(id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Order order = response.body();
                     if (order.getStatus().equalsIgnoreCase("Giao h??ng th??nh c??ng")) {
                         holder.tv_danhgiasp.setVisibility(View.VISIBLE);
                         holder.tv_danhgiasp.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 Intent intent = new Intent(context, ReviewActivity.class);
                                 intent.putExtra("id", productsList.get(position).getProductId());
                                 intent.putExtra("size", productsList.get(position).getSize());
                                 intent.putExtra("color", productsList.get(position).getColor());
                                 context.startActivity(intent);
                             }
                         });
                    } else if (order.getStatus().equalsIgnoreCase("??ang ch??? x??c nh???n")) {
                         holder.tv_danhgiasp.setVisibility(View.GONE);
                     }
                     else if (order.getStatus().equalsIgnoreCase("??ang chu???n b??? h??ng")) {
                         holder.tv_danhgiasp.setVisibility(View.GONE);
                     } else if (order.getStatus().equalsIgnoreCase("??ang giao h??ng")) {
                         holder.tv_danhgiasp.setVisibility(View.GONE);
                     } else if (order.getStatus().equalsIgnoreCase("Ng?????i d??ng ???? h???y ????n h??ng")) {
                         holder.tv_danhgiasp.setVisibility(View.GONE);
                     }

                } else {
                    holder.tv_danhgiasp.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(context, "Vui l??ng ki???m tra l???i k???t n???i internet", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tv_title_product_detail_order.setMaxLines(1);
        holder.tv_title_product_detail_order.setEllipsize(TextUtils.TruncateAt.END);
    }

    @Override
    public int getItemCount() {
        if (productsList == null) {
            return 0;
        }
        return productsList.size();
    }

    public class Viewholoder extends RecyclerView.ViewHolder {
        RelativeLayout layout_item_order_detail;
        ImageView img_product_detail_order;
        TextView tv_thanhtien_product_detail_order,
                tv_soluong_product_detail_order,
                tv_title_product_detail_order,
                tv_sizecolor_product_detail, tv_danhgiasp;

        public Viewholoder(@NonNull View itemView) {
            super(itemView);
            Viewholoder viewholoder = null;
            img_product_detail_order = (ImageView) itemView.findViewById(R.id.img_product_detail_order);
            tv_thanhtien_product_detail_order = itemView.findViewById(R.id.tv_thanhtien_product_detail_order);
            tv_soluong_product_detail_order = itemView.findViewById(R.id.tv_soluong_product_detail_order);
            tv_title_product_detail_order = itemView.findViewById(R.id.tv_title_product_detail_order);
            tv_sizecolor_product_detail = itemView.findViewById(R.id.tv_sizecolor_product_detail);
            tv_danhgiasp = itemView.findViewById(R.id.tv_danhgiasp);
            layout_item_order_detail = itemView.findViewById(R.id.layout_item_order_detail);

        }

    }
}
