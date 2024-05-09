package com.example.siki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.model.Cart;
import com.example.siki.model.Order;
import com.example.siki.model.OrderDetail;
import com.example.siki.model.Product;
import com.example.siki.utils.PriceFormatter;

import java.util.ArrayList;
import java.util.List;

public class OrderRecycleAdapter extends RecyclerView.Adapter<OrderRecycleAdapter.OrderHolder> {
    private List<Order> orders = new ArrayList<>();

    private Context context ;

    public OrderRecycleAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order = orders.get(position);
        holder.tv_order_id.setText(order.getId() + "");
        holder.tv_order_address.setText(order.getReceiverAddress());
        holder.tv_order_createdAt.setText(order.getCreatedAt().toString());
        holder.tv_order_status.setText(order.getStatus().toString());
        Double totalPrice = getTotalPriceByOrder(order);
        String totalPriceString = PriceFormatter.formatDouble(totalPrice);
        holder.tv_order_totalPrice.setText(totalPriceString);
    }

    private Double getTotalPriceByOrder(Order order) {
        if (order == null || order.getOrderDetails() == null) {
            return 0.0;
        }

        Double totalPrice = 0.0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (orderDetail != null && orderDetail.getPrice() != null) {
                totalPrice += orderDetail.getPrice();
            }
        }
        return totalPrice;
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderHolder extends RecyclerView.ViewHolder{
        TextView tv_order_id, tv_order_address, tv_order_createdAt, tv_order_status, tv_order_totalPrice;
        Button btn_order_view, btn_order_edit, btn_order_delete;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            tv_order_id = itemView.findViewById(R.id.tv_order_id);
            tv_order_address = itemView.findViewById(R.id.tv_order_address);
            tv_order_createdAt = itemView.findViewById(R.id.tv_order_createdAt);
            tv_order_status = itemView.findViewById(R.id.tv_order_status);
            tv_order_totalPrice = itemView.findViewById(R.id.tv_order_totalPrice);
            btn_order_view = itemView.findViewById(R.id.btn_order_view);
            btn_order_edit = itemView.findViewById(R.id.btn_order_edit);
            btn_order_delete = itemView.findViewById(R.id.btn_order_delete);
        }
    }
}
