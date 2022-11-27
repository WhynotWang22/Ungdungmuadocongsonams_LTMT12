package com.whynotquang.ungdungmuadocongsonam_ltmt12.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.R;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.activity.ChitietActivity;
import com.whynotquang.ungdungmuadocongsonam_ltmt12.model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchAdapter extends ArrayAdapter<Product>{
    Context context;
    List<Product> sanPhamList;

    public ProductSearchAdapter(@NonNull Context context, int resource, List<Product> sanPhamList) {
        super(context, resource);
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        }

        ImageView imgSearch = convertView.findViewById(R.id.img_search);
        TextView tvSearchName = convertView.findViewById(R.id.tv_search_name);
        TextView tvSearchPrice = convertView.findViewById(R.id.tv_search_price);
        RelativeLayout layout_item_search = convertView.findViewById(R.id.layout_item_search);

        Product product = getItem(position);

        Glide.with(imgSearch.getContext()).load(product.getImg()).into(imgSearch);
        tvSearchName.setText(product.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvSearchPrice.setText(decimalFormat.format(product.getPrice())+"đ");
        layout_item_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChitietActivity.class);
                intent.putExtra("id", product.get_id());
                getContext().startActivity(intent);
                Log.d("aaa","id_search "+product.get_id());
            }
        });
        return convertView;
    }
    @NonNull
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Product> listSuggest = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    listSuggest.addAll(sanPhamList);
                } else {
                    String filter = constraint.toString().toLowerCase().trim();
                    for (Product p : sanPhamList) {
                        if (p.getTitle().toLowerCase().contains(filter)) {
                            listSuggest.add(p);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listSuggest;
                filterResults.count = listSuggest.size();

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List<Product>) results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Product) resultValue).getTitle();
            }
        };
    }
}