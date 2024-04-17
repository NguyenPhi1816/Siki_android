package com.example.siki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.siki.R;
import com.example.siki.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewSearchApdapter extends ArrayAdapter<Product> {

    List<Product> fillter_data = null;
    List<Product> data;
    public ListViewSearchApdapter(@NonNull Context context, List<Product> data) {
        super(context, R.layout.item_searchview, data);
        this.data =data;
        this.fillter_data = new ArrayList<>();
        this.fillter_data.addAll(data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Product data= getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_searchview, parent, false);
        }

        TextView tvTenSP = convertView.findViewById(R.id.item_name_search);
        TextView tvGiaSP = convertView.findViewById(R.id.item_price_search);
        ImageView ivSanPham = convertView.findViewById(R.id.imagesearchitem);

        if (data !=null) {
            tvGiaSP.setText(data.getName());
            tvTenSP.setText(String.valueOf(data.getPrice()));
            Picasso.get().load(data.getImagePath()).into(ivSanPham);
        }
        return convertView;
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if (charText.length() == 0) {
//            data.addAll(fillter_data);
        } else {
            for (Product wp : fillter_data) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
