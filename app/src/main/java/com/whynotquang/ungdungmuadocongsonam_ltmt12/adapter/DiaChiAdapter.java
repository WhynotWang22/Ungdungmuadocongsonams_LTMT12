package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Address;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.ItemClickAddressListener;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.InterFace.ItemClickListener;

import java.util.List;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiHolder> {

    List<Address> addressList;
    int selectedPosition = -1;
    ItemClickAddressListener itemClickAddressListener;

    public DiaChiAdapter(List<Address> addressList, ItemClickAddressListener itemClickAddressListener) {
        this.addressList = addressList;
        this.itemClickAddressListener = itemClickAddressListener;
    }

    @NonNull
    @Override
    public DiaChiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diachi_checkout,parent,false);
        return new DiaChiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaChiHolder holder, int position) {
        Address address = addressList.get(position);
        holder.tv_name_diachi.setText(address.getName());
        holder.tv_diachi_diachi.setText(address.getDetailAddress());
        holder.tv_sdt_diachi.setText(String.valueOf(address.getNumberPhone()));
        holder.radioButton.setChecked(position == selectedPosition);
//        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    selectedPosition = holder.getAdapterPosition();
//                    notifyDataSetChanged();
//                    Toast.makeText(context, "data: "+holder.tv_name_diachi.getText().toString()+"dia chi "+holder.tv_diachi_diachi.getText().toString()+"sdt "+holder.tv_sdt_diachi.getText().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                notifyItemRangeChanged(0, addressList.size());
                itemClickAddressListener.onClickAddress(address);
            }
        };
        holder.cardView.setOnClickListener(clickListener);
        holder.radioButton.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }
}
