package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Address;

import java.util.List;

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

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {
        Address address = addressList.get(position);
        holder.tv_name_address.setText(address.getName());
        holder.tv_sdt_address.setText(String.valueOf(address.getNumberPhone()));
        holder.tv_diachi_address.setText(address.getDetailAddress());

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }
}
