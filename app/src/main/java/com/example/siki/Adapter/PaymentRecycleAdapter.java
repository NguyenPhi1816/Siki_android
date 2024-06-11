package com.example.siki.Adapter;

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
import com.example.siki.model.Product;
import com.example.siki.utils.PriceFormatter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentRecycleAdapter extends RecyclerView.Adapter<PaymentRecycleAdapter.PaymentHolder> {
    private List<Cart> cartList;

    private final String quantityFormat = "x    %d";

    private final String quantityProductFormat = "Tổng số tiền (%d sản phẩm):";

    public PaymentRecycleAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public PaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item, parent, false);
        return new PaymentRecycleAdapter.PaymentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHolder holder, int position) {
        Cart cart = cartList.get(position);
        Product product = cart.getProduct();
        Picasso.get().load(product.getImagePath()).into(holder.payment_product_image);
        double productPrice = cart.getProduct().getPrice();
        String productPriceString = PriceFormatter.formatDouble(productPrice);
        double totalPrice = productPrice * cart.getQuantity();
        String totalPriceString = PriceFormatter.formatDouble(totalPrice);
        holder.tv_payment_quantity.setText(String.format(quantityProductFormat, cart.getQuantity()));
        holder.payment_store_name.setText(cart.getProduct().getStore().getName());
        holder.payment_product_price.setText(productPriceString);
        holder.payment_product_quantity.setText(String.format(quantityFormat, cart.getQuantity()));
        holder.payment_product_name.setText(cart.getProduct().getName());
        holder.payment_totalPrice.setText(totalPriceString);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class PaymentHolder extends RecyclerView.ViewHolder{

        ImageView payment_product_image;
        TextView payment_store_name, tv_payment_quantity, payment_product_name, payment_product_price,payment_product_quantity, payment_totalPrice;

        public PaymentHolder(@NonNull View itemView) {
            super(itemView);
            tv_payment_quantity = itemView.findViewById(R.id.tv_payment_quantity);
            payment_product_image = itemView.findViewById(R.id.payment_product_image);
            payment_store_name = itemView.findViewById(R.id.payment_store_name);
            payment_product_name = itemView.findViewById(R.id.payment_product_name);
            payment_product_price = itemView.findViewById(R.id.payment_product_price);
            payment_product_quantity = itemView.findViewById(R.id.payment_product_quantity);
            payment_totalPrice = itemView.findViewById(R.id.payment_totalPrice);
        }
    }
}
