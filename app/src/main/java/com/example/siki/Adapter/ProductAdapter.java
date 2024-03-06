package com.example.siki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.model.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private List<Product> productList;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productList.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View productView;
        if (view == null) {
            productView = View.inflate(viewGroup.getContext(), R.layout.product_item, null);
        } else productView = view;

        //Bind sữ liệu phần tử vào View
        Product product = productList.get(position);
        ((TextView) productView.findViewById(R.id.productName)).setText(String.format("Tên SP: %s", product.getName()));
        ((TextView) productView.findViewById(R.id.productPrice)).setText(String.format("Giá SP: %s", product.getProductPrice().getPrice()));
        ((ImageView) productView.findViewById(R.id.productImage)).setImageResource(R.drawable.samsung);

        return productView;
    }


}
