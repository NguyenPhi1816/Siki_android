package com.example.siki.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.siki.R;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;

import java.util.List;

public class CartAdapter extends BaseAdapter {


    private List<Product> productList ;

    public CartAdapter() {
    }

    public CartAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCart;
        if (convertView == null) {
            viewCart = View.inflate(parent.getContext(), R.layout.cart_item, null);
        } else viewCart = convertView;
        Product product = (Product) getItem(position);
        ((CheckBox) viewCart.findViewById(R.id.cart_cb)).setChecked(false);
        ((TextView) viewCart.findViewById(R.id.cart_name)).setText(product.getName());
        ((TextView) viewCart.findViewById(R.id.cart_price)).setText(product.getProductPrice().getPrice()+"");
        ((ImageView) viewCart.findViewById(R.id.cart_image)).setImageResource(R.drawable.samsung);
        return viewCart;
    }
}
