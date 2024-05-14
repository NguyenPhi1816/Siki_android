package com.example.siki.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.model.OrderDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserOrderDetailAdapter extends  RecyclerView.Adapter<UserOrderDetailAdapter.OrderViewHolder>{
    private List<OrderDetail> orderDetails;

    public UserOrderDetailAdapter(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_order_detail, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderDetail currentOrderDetail = orderDetails.get(position);
        String imagePath = currentOrderDetail.getProduct().getImagePath();
        String productName = currentOrderDetail.getProduct().getName();
        int productQuantity = currentOrderDetail.getQuantity();
        int orderTotalPrice = currentOrderDetail.getPrice().intValue() * currentOrderDetail.getQuantity();

        Picasso.get().load(imagePath).resize(200, 200).into(holder.ivProduct);
        holder.tvProductName.setText(productName);
        holder.tvQuantity.setText("Số lượng: " + String.valueOf(productQuantity));
        holder.tvPrice.setText("Thành tiền: " + orderTotalPrice + "đ");
    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        // Khai báo views
        TextView tvProductName, tvQuantity, tvPrice;
        ImageView ivProduct;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.imageViewProduct);
            tvProductName = itemView.findViewById(R.id.textViewProductName);
            tvQuantity = itemView.findViewById(R.id.textViewQuantity);
            tvPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}
