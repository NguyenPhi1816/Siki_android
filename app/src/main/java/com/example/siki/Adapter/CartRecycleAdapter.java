package com.example.siki.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.activities.CartActivity;
import com.example.siki.database.CartDatasource;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.utils.PriceFormatter;

import java.util.List;

public class CartRecycleAdapter extends RecyclerView.Adapter<CartRecycleAdapter.CartHolder> {
    private List<Cart> cartList ;

    private Context context;


    public CartRecycleAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewCart = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartHolder(viewCart);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart = cartList.get(position);
        Product product = cart.getProduct();
        // Todo: set isChosen of cart => completed
        int currentQuantity = cart.getQuantity();
        holder.btn_cart_minus.setEnabled(cart.getQuantity() > 1);
        holder.btn_cart_minus.setHovered(cart.getQuantity() > 1);
        holder.cartCheckbox.setChecked(cart.isChosen());
        holder.cartImage.setImageResource(R.drawable.samsung);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(PriceFormatter.formatDouble(product.getPrice() * cart.getQuantity()) +" d");
        holder.tv_cart_quantity.setText(currentQuantity+"");
        CartDatasource cartDatasource = new CartDatasource(context);
        cartDatasource.open();

        holder.cartCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.cartCheckbox.setChecked(isChecked);
                cartDatasource.updateSelectedCart(cart.getId(), isChecked);
                if (context instanceof CartActivity) {
                    ((CartActivity)context).readDb();
                }
            }
        });
        holder.btn_cart_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_cart_quantity.setText((currentQuantity + 1)+"");
                cartDatasource.updateCartQuantity(cart.getId(), currentQuantity + 1 );
                if (context instanceof CartActivity) {
                    ((CartActivity)context).readDb();
                }
            }
        });

        holder.btn_cart_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_cart_quantity.setText((currentQuantity - 1)+"");
                cartDatasource.updateCartQuantity(cart.getId(), currentQuantity - 1 );
                if (context instanceof CartActivity) {
                    ((CartActivity)context).readDb();
                }
            }
        });

        holder.delete_total_carts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartDatasource.remove(cart.getId());
                if (context instanceof CartActivity) {
                    ((CartActivity)context).readDb();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartHolder extends RecyclerView.ViewHolder{

        ImageView cartImage;
        TextView productName, productPrice;

        Button btn_cart_minus, tv_cart_quantity, btn_cart_add, delete_total_carts;

        CheckBox cartCheckbox;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            cartCheckbox = itemView.findViewById(R.id.cb_cart);
            cartImage = itemView.findViewById(R.id.cart_image);
            productName = itemView.findViewById(R.id.cart_name);
            productPrice = itemView.findViewById(R.id.cart_price);

            btn_cart_minus = itemView.findViewById(R.id.btn_cart_minus);
            tv_cart_quantity = itemView.findViewById(R.id.tv_cart_quantity);
            btn_cart_add = itemView.findViewById(R.id.btn_cart_add);
            delete_total_carts = itemView.findViewById(R.id.delete_total_carts);
        }
    }
}
