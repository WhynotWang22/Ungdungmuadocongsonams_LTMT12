package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.InterFace.CallBack_Update_Delete;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Products;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholoder> {
    List<Products> productsList;
    Context context;
    private CallBack_Update_Delete callBackUpdateDelete;
    private  int value ;

    public CartAdapter(List<Products> productsList, Context context, CallBack_Update_Delete callBackUpdateDelete) {
        this.callBackUpdateDelete = callBackUpdateDelete;
        this.productsList = productsList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.Viewholoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new Viewholoder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholoder holder, int position) {
        Products product = productsList.get(position);
        value = Integer.parseInt(Integer.toString(product.getQuantity()));
        holder.tvTitleCart.setText(product.getTitle());
        holder.tv_id_test.setText(product.get_id());
        holder.tv_values.setText(Integer.toString(product.getQuantity()));
        holder.tvSizeCart.setText("Size:" + product.getSize());
        holder.tvColorCart.setText("Color:" + product.getColor());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###");
        holder.tv_price_cart.setText("Giá:" + decimalFormat.format(product.getPrice()) + "Đ");
        Glide.with(context).load(product.getProductIMG()).error(R.drawable.mau).into(holder.img_cart);

        holder.layout_delete_item_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             callBackUpdateDelete.onclickDelete(product);
            }
        });

        holder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = holder.tv_values.getText().toString();
                value = Integer.parseInt(currentValue);
                value++;
                int tong = 0;
                tong = value * product.getPrice();
                holder.tv_price_cart.setText("Giá:" + decimalFormat.format(tong) + "Đ");
                callBackUpdateDelete.tong(tong);
                holder.tv_values.setText(String.valueOf(value));
                int sl = (Integer.parseInt(holder.tv_values.getText().toString()));

                if (sl <= 1) {
                    holder.btnminus.setVisibility(View.INVISIBLE);
                }else {
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
                int tong = 0;
                tong = value * product.getPrice();
                holder.tv_price_cart.setText("Giá:" + decimalFormat.format(tong) + "Đ");
                callBackUpdateDelete.tong(tong);
                int sl = (Integer.parseInt(holder.tv_values.getText().toString()));

                if (sl <= 1) {
                    holder.btnminus.setVisibility(View.INVISIBLE);
                }else {
                    holder.btnminus.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class Viewholoder extends RecyclerView.ViewHolder {
        ImageView img_cart;
        LinearLayout layout_delete_item_cart;
        TextView tvTitleCart;
        TextView tv_id_test;
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
            tv_id_test = (TextView) itemView.findViewById(R.id.tv_id_test);
            tvColorCart = (TextView) itemView.findViewById(R.id.tv_color_cart);
            tv_price_cart = (TextView) itemView.findViewById(R.id.tv_price_cart);
            btnminus = (Button) itemView.findViewById(R.id.btnminus);
            tv_values = (TextView) itemView.findViewById(R.id.tv_values);
            btnplus = (Button) itemView.findViewById(R.id.btnplus);


        }
    }
}
