package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.Constain.AppConstain;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.api.ApiService;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.even.Even;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Address;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddressAdapter extends RecyclerView.Adapter<AddressHolder> {
    Context context;
    List<Address> addressList;

    public AddressAdapter(Context context, List<Address> addressList) {
        this.context = context;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address,parent,false);
        return new AddressHolder(view);
    }
    protected void refreshData(int position) {
        addressList.remove(position);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, @SuppressLint("RecyclerView") int position) {
        Address address = addressList.get(position);
        holder.tv_name_address.setText(address.getName());
        holder.tv_sdt_address.setText(String.valueOf(address.getNumberPhone()));
        holder.tv_diachi_address.setText(address.getDetailAddress());
        holder.layout_delete_item_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
                String token = sp.getString("token", "");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstain.BASE_URL + "address/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService apiService = retrofit.create(ApiService.class);
                apiService.deleteAddress(token,address.getId()).enqueue(new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {
                        refreshData(position);
                        Toast.makeText(context, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        Toast.makeText(context, "that bai", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

}
