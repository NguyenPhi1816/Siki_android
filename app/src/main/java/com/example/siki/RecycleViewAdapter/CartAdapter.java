package com.example.siki.RecycleViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context mContext;
    private List<Cart> cartList;

    public CartAdapter(Context mContext, List<Cart> cartList) {
        this.mContext = mContext;
        this.cartList = cartList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cartCheckbox;
        private ImageView cartImage;
        private TextView cartName, cartPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartCheckbox = itemView.findViewById(R.id.cart_cb);
            cartImage = itemView.findViewById(R.id.cart_image);
            cartName = itemView.findViewById(R.id.cart_name);
            cartPrice = itemView.findViewById(R.id.cart_price);
        }
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.cart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.cartName.setText(cart.getProduct().getName());
        holder.cartImage.setImageResource(R.drawable.samsung);
        holder.cartPrice.setText(cart.getProduct().getProductPrice().getPrice() + "");
        holder.cartCheckbox.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
