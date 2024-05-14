package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.siki.Adapter.OnOrderButtonClickListener;
import com.example.siki.Adapter.UserOrderDetailAdapter;
import com.example.siki.Adapter.UserOrdersAdapter;
import com.example.siki.R;
import com.example.siki.database.OrderDataSource;
import com.example.siki.database.OrderDetailDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Order;
import com.example.siki.model.OrderDetail;
import com.example.siki.model.User;

import java.util.Optional;

public class UserOrderDetailActivity extends AppCompatActivity {
    private OrderDataSource orderDataSource;
    private OrderDetailDatasource orderDetailDatasource;
    private ProductDatabase productDatabase;
    private UserDataSource userDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the icon for the back button (should be a left chevron)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left);  // Replace with your own icon here
        }

        readDb();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to previous one if there is any
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void readDb(){
        TextView tvStatus, tvReceiverName, tvReceiverPhoneNumber, tvReceiverAddress, tvOrderId, tvCreateAt, tvTotalPrice;
        tvStatus = findViewById(R.id.tvStatus);
        tvReceiverName = findViewById(R.id.tvReceiverName);
        tvReceiverPhoneNumber = findViewById(R.id.tvReceiverPhoneNumber);
        tvReceiverAddress = findViewById(R.id.tvReceiverAddress);
        tvOrderId = findViewById(R.id.tvOrderId);
        tvCreateAt = findViewById(R.id.tvCreateAt);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);


        Intent intent = getIntent();
        Long orderId = intent.getLongExtra("order_id", 0);
        System.out.println( "receive order_id: " + orderId);

        orderDataSource = new OrderDataSource(this);
        orderDataSource.open();
        orderDetailDatasource = new OrderDetailDatasource(this);
        orderDetailDatasource.open();
        productDatabase = new ProductDatabase(this);
        productDatabase.open();
        userDataSource = new UserDataSource(this);
        userDataSource.open();

        Optional<Order> order = orderDataSource.findById(orderDetailDatasource, productDatabase, userDataSource, orderId);
        if(order.isPresent()) {
            tvOrderId.setText("Mã đơn hàng: " + order.get().getId());
            tvCreateAt.setText("Thời gian đặt hàng: " + order.get().getCreatedAt());

            String status = "";
            switch(order.get().getStatus()) {
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

            tvStatus.setText(status);

            User user = order.get().getUser();
            tvReceiverName.setText("Họ và tên: " + user.getFullname());
            tvReceiverPhoneNumber.setText("Số điện thoại: " + user.getPhoneNumber());
            tvReceiverAddress.setText("Địa chỉ: " + user.getAddress());

            double totalPrice = 0D;
            for (OrderDetail od:order.get().getOrderDetails()) {
                totalPrice += od.getPrice() * od.getQuantity();
            }
            tvTotalPrice.setText(String.valueOf((int) totalPrice) + "đ");

            RecyclerView recyclerView = findViewById(R.id.rvUserOrderDetail);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            UserOrderDetailAdapter userOrderDetailAdapter = new UserOrderDetailAdapter(order.get().getOrderDetails());
            recyclerView.setAdapter(userOrderDetailAdapter);
        }

        orderDataSource.close();
        orderDetailDatasource.close();
        productDatabase.close();
        userDataSource.close();
    };
}