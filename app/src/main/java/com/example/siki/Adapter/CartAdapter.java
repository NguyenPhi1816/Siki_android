package com.example.siki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.model.Cart;

import java.util.List;

public class CartAdapter extends BaseAdapter {


    private List<Cart> cartList ;

    public CartAdapter() {
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cartList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewCart;
        if (convertView == null) {
            viewCart = View.inflate(parent.getContext(), R.layout.cart_item, null);
        } else viewCart = convertView;
        Cart cart = (Cart) getItem(position);
        System.out.println(cart.getProduct().getName());
        System.out.println( cart.getProduct().getProductPrice().getPrice()+"");
        ((CheckBox) viewCart.findViewById(R.id.cart_cb)).setChecked(false);
        ((TextView) viewCart.findViewById(R.id.cart_name)).setText(cart.getProduct().getName());
        ((TextView) viewCart.findViewById(R.id.cart_price)).setText(cart.getProduct().getProductPrice().getPrice()+"");
        ((ImageView) viewCart.findViewById(R.id.cart_image)).setImageResource(R.drawable.samsung);
        return viewCart;
    }
}
