package com.example.siki.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.activities.OtpActivity;
import com.example.siki.activities.UserOrderDetailActivity;
import com.example.siki.model.Order;
import com.example.siki.model.OrderDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserOrdersAdapter extends  RecyclerView.Adapter<UserOrdersAdapter.OrderViewHolder>{
    private List<Order> orders;
    private OnOrderButtonClickListener onOrderButtonClickListener;

    public UserOrdersAdapter(List<Order> orders, OnOrderButtonClickListener  onOrderButtonClickListener) {
        this.orders = orders;
        this.onOrderButtonClickListener = onOrderButtonClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order currentOrder = orders.get(position);
        String imagePath = currentOrder.getOrderDetails().get(0).getProduct().getImagePath();
        String productName = currentOrder.getOrderDetails().get(0).getProduct().getName();
        int productQuantity = currentOrder.getOrderDetails().get(0).getQuantity();
        Double productPrice = currentOrder.getOrderDetails().get(0).getPrice();
        int orderTotalPrice = 0;
        for(OrderDetail orderDetail: currentOrder.getOrderDetails()) {
            orderTotalPrice += orderDetail.getPrice().intValue() * orderDetail.getQuantity();
        }

        String status = "";
        switch(currentOrder.getStatus()) {
            case Pending:
                status = "Đang chờ xác nhận";
                break;
            case Shipping:
                status = "Đang giao";
                break;
            case Success:
                status = "Giao hàng thành công";
                break;
            default:
                status = "Có lỗi xảy ra";
                break;
        }

        Picasso.get().load(imagePath).resize(200, 200).into(holder.ivProduct);
        holder.tvProductName.setText(productName);
        holder.tvQuantity.setText("Số lượng: " + String.valueOf(productQuantity));
        holder.tvStatus.setText("Tình trạng: " + status);
        holder.tvPrice.setText("Giá sản phẩm: " + String.valueOf(productPrice.intValue() * productQuantity) + "đ");
        holder.tvTotalPrice.setText("Thành tiền: " + orderTotalPrice + "đ");
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOrderButtonClickListener.onOrderButtonClick(currentOrder.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        // Khai báo views
        TextView tvProductName, tvQuantity, tvStatus, tvPrice, tvTotalPrice;
        ImageView ivProduct;
        Button btnDetail;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.imageViewProduct);
            tvProductName = itemView.findViewById(R.id.textViewProductName);
            tvQuantity = itemView.findViewById(R.id.textViewQuantity);
            tvStatus = itemView.findViewById(R.id.textViewStatus);
            tvPrice = itemView.findViewById(R.id.textViewPrice);
            tvTotalPrice = itemView.findViewById(R.id.textViewTotalPrice);
            btnDetail = itemView.findViewById(R.id.btn_detail);
        }
    }
}
