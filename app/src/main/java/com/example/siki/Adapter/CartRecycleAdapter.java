package com.example.siki.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;

import java.util.List;

public class CartRecycleAdapter extends RecyclerView.Adapter<CartRecycleAdapter.CartHolder> {
    private List<Product> productList ;

    private Context context;


    public CartRecycleAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCart = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartHolder(viewCart);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Product product = productList.get(position);
        holder.cartImage.setImageResource(R.drawable.samsung);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getProductPrice().getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CartHolder extends RecyclerView.ViewHolder{

        ImageView cartImage;
        TextView productName, productPrice;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.cart_image);
            productName = itemView.findViewById(R.id.cart_name);
            productPrice = itemView.findViewById(R.id.cart_price);
        }
    }
}
