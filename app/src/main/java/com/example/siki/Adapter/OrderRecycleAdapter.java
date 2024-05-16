package com.example.siki.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.siki.R;
import com.example.siki.activities.OrderDetailActivity;
import com.example.siki.activities.OrderEditActivity;
import com.example.siki.database.AccountDataSource;
import com.example.siki.database.OrderDataSource;
import com.example.siki.enums.OrderStatus;
import com.example.siki.enums.Role;
import com.example.siki.model.Account;
import com.example.siki.model.Order;
import com.example.siki.model.OrderDetail;
import com.example.siki.utils.DateFormatter;
import com.example.siki.utils.PriceFormatter;
import com.example.siki.variable.GlobalVariable;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

import java.util.ArrayList;
import java.util.List;

public class OrderRecycleAdapter extends RecyclerView.Adapter<OrderRecycleAdapter.OrderHolder> {
    private List<Order> orders = new ArrayList<>();


    private Context context ;
    private Account account;

    public OrderRecycleAdapter(List<Order> orders, Context context, Account account) {
        this.orders = orders;
        this.context = context;
        this.account = account;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        if (account != null) {
            if (account.getRole().equals(Role.USER.toString())) {
                holder.btn_order_edit.setVisibility(View.INVISIBLE);
                holder.btn_order_delete.setVisibility(View.INVISIBLE);
            }else {
                holder.btn_order_edit.setVisibility(View.VISIBLE);
                holder.btn_order_delete.setVisibility(View.VISIBLE);
            }
        }

        Order order = orders.get(position);
        holder.tv_order_id.setText(order.getId() + "");
        holder.tv_order_address.setText(order.getReceiverAddress());
        holder.tv_order_createdAt.setText(DateFormatter.formatLocalDateTimeToString(order.getCreatedAt()));
        holder.tv_order_status.setText(translateStatus(order.getStatus()));
        Double totalPrice = getTotalPriceByOrder(order);
        String totalPriceString = PriceFormatter.formatDouble(totalPrice);
        holder.tv_order_totalPrice.setText(totalPriceString);
        holder.btn_order_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToOrderDetailPage(order.getId());
            }
        });
        holder.btn_order_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToOrderEditPage(order);
            }
        });
        holder.btn_order_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog(order.getId());
            }
        });
    }

    private String translateStatus (OrderStatus orderStatus) {
        switch(orderStatus) {
            case Pending:
                return "CHỜ THANH TOÁN";
            case Shipping:
                return "ĐANG VẬN CHUYỂN";
            case Success:
                return "THÀNH CÔNG";
            default:
                break;
        }
        return "";
    }

    private void deleteOrder(Long orderId) {
        OrderDataSource orderDataSource = new OrderDataSource(context);
        orderDataSource.open();
        orderDataSource.deleteOrder(orderId);
    }

    private void redirectToOrderDetailPage(Long orderId) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("orderId", orderId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }



    private void redirectToOrderEditPage(Order order) {
        Intent intent = new Intent(context, OrderEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("order", order);
        intent.putExtra("order", bundle);
        context.startActivity(intent);
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

    private void showConfirmDialog (Long orderId) {
        PopupDialog.getInstance(context)
                .standardDialogBuilder()
                .createIOSDialog()
                .setHeading("Lưu ý")
                .setDescription("Bạn có chắc chắn muốn thực hiện không?")
                .build(new StandardDialogActionListener() {
                    @Override
                    public void onPositiveButtonClicked(Dialog dialog) {
                        deleteOrder(orderId);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
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
