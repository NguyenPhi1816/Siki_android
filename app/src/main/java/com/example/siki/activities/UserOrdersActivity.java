package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.siki.Adapter.OnOrderButtonClickListener;
import com.example.siki.Adapter.UserOrdersAdapter;
import com.example.siki.R;
import com.example.siki.database.OrderDataSource;
import com.example.siki.database.OrderDetailDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Order;

import java.util.List;

public class UserOrdersActivity extends AppCompatActivity {
    private UserDataSource userDataSource;
    private ProductDatabase productDatabase;
    private OrderDataSource orderDataSource;
    private OrderDetailDatasource orderDetailDatasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders);

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

    private void readDb () {
        Intent intent = getIntent();
        int userId = intent.getIntExtra("user_id", 0);

        userDataSource = new UserDataSource(this);
        userDataSource.open();
        productDatabase = new ProductDatabase(this);
        productDatabase.open();
        orderDataSource = new OrderDataSource(this);
        orderDataSource.open();
        orderDetailDatasource = new OrderDetailDatasource(this);
        orderDetailDatasource.open();


        List<Order> orders = orderDataSource.findByUserId(orderDetailDatasource, productDatabase, userDataSource, userId);

        for (Order order: orders) {
            System.out.println(order.toString());
        }

        RecyclerView recyclerView = findViewById(R.id.rvUserOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UserOrdersAdapter adapter = new UserOrdersAdapter(orders, new OnOrderButtonClickListener() {
            @Override
            public void onOrderButtonClick(Long orderId) {
                System.out.println("send order_id: " +  orderId);
                Intent intent = new Intent(UserOrdersActivity.this, UserOrderDetailActivity.class);
                intent.putExtra("order_id", orderId); // Đặt orderId vào Intent
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        userDataSource.close();
        productDatabase.close();
        orderDataSource.close();
    }
}