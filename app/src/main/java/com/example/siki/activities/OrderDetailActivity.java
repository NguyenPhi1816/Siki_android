package com.example.siki.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.siki.Adapter.OrderDetailRecycleAdapter;
import com.example.siki.Adapter.PaymentRecycleAdapter;
import com.example.siki.R;
import com.example.siki.database.OrderDataSource;
import com.example.siki.database.OrderDetailDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Order;
import com.example.siki.model.OrderDetail;
import com.example.siki.utils.DateFormatter;
import com.example.siki.utils.PriceFormatter;

import java.util.List;
import java.util.Optional;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView tv_orderDetail_order_id, tv_orderDetail_order_createdAt, tv_orderDetail_order_status,
            tv_order_detail_receiverName, tv_order_detail_receiverPhoneNumber, tv_order_detail_receiverAddress, tv_order_detail_order_note,
            tv_order_detail_order_totalPrice;

    private RecyclerView rc_order_detail;

    private Button btn_back_to_order_management;

    private OrderDetailRecycleAdapter orderDetailRecycleAdapter;

    private Long orderId = 0L;
    private Order order = null;

    private OrderDataSource orderDataSource;
    private OrderDetailDatasource orderDetailDatasource;
    private ProductDatabase productDatabase;
    private UserDataSource userDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setControl();
        setEvent();
    }

    private void setEvent() {
        if (order != null) {
            if (!order.getOrderDetails().isEmpty()) {
                List<OrderDetail> orderDetails = order.getOrderDetails();
                orderDetailRecycleAdapter = new OrderDetailRecycleAdapter(orderDetails);
                rc_order_detail.setAdapter(orderDetailRecycleAdapter);
                rc_order_detail.setLayoutManager(new GridLayoutManager(this, 1));
            }
        }

        btn_back_to_order_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailActivity.this, OrderManagementActivity.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        readDb();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void readDb() {
        if (orderId != 0L) {
            userDataSource = new UserDataSource(this);
            userDataSource.open();
            productDatabase = new ProductDatabase(this);
            productDatabase.open();
            orderDataSource = new OrderDataSource(this);
            orderDataSource.open();
            orderDetailDatasource = new OrderDetailDatasource(this);
            orderDetailDatasource.open();
            Optional<Order> newOrder = orderDataSource.findById(orderDetailDatasource, productDatabase, userDataSource, orderId);
            if (newOrder.isPresent()) {
                order = newOrder.get();
                tv_orderDetail_order_id.setText(order.getId() + "");
                tv_orderDetail_order_createdAt.setText(DateFormatter.formatLocalDateTimeToString(order.getCreatedAt()));
                tv_orderDetail_order_status.setText(order.getStatus().toString());
                tv_order_detail_receiverName.setText(order.getReceiverName());
                tv_order_detail_receiverPhoneNumber.setText(order.getReceiverPhoneNumber());
                tv_order_detail_receiverAddress.setText(order.getReceiverAddress());
                tv_order_detail_order_note.setText(order.getNote());
                Double totalPrice = getTotalPriceByOrder(order);
                tv_order_detail_order_totalPrice.setText(PriceFormatter.formatDouble(totalPrice));

                List<OrderDetail> orderDetails = order.getOrderDetails();
                orderDetailRecycleAdapter = new OrderDetailRecycleAdapter(orderDetails);
                rc_order_detail.setAdapter(orderDetailRecycleAdapter);
                rc_order_detail.setLayoutManager(new GridLayoutManager(this, 1));
            }
        }
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

    private void setControl() {
        tv_orderDetail_order_id = findViewById(R.id.tv_orderDetail_order_id);
        tv_orderDetail_order_createdAt = findViewById(R.id.tv_orderDetail_order_createdAt);
        tv_orderDetail_order_status = findViewById(R.id.tv_orderDetail_order_status);
        tv_order_detail_receiverName = findViewById(R.id.tv_order_detail_receiverName);
        tv_order_detail_receiverPhoneNumber = findViewById(R.id.tv_order_detail_receiverPhoneNumber);
        tv_order_detail_receiverAddress = findViewById(R.id.tv_order_detail_receiverAddress);
        tv_order_detail_order_note = findViewById(R.id.tv_order_detail_order_note);
        tv_order_detail_order_totalPrice = findViewById(R.id.tv_order_detail_order_totalPrice);
        rc_order_detail = findViewById(R.id.rc_order_detail);
        btn_back_to_order_management = findViewById(R.id.btn_back_to_order_management);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderId = extras.getLong("orderId");
            System.out.println(orderId);
        }
    }
}