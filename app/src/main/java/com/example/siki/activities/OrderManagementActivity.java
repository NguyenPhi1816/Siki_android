package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.siki.R;
import com.example.siki.database.OrderDataSource;
import com.example.siki.database.OrderDetailDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Order;
import com.example.siki.model.OrderDetail;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.List;

public class OrderManagementActivity extends AppCompatActivity {
    private RecyclerView order_recycle_view;
    private List<Order> orders = new ArrayList<>();

    private UserDataSource userDataSource ;
    private ProductDatabase productDatabase;
    private OrderDataSource orderDataSource;
    private OrderDetailDatasource orderDetailDatasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);
        setControl();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readDb();
    }

    private void readDb() {
        // get all order
        userDataSource = new UserDataSource(this);
        userDataSource.open();
        productDatabase = new ProductDatabase(this);
        productDatabase.open();

        orderDataSource = new OrderDataSource(this);
        orderDataSource.open();
        orderDetailDatasource = new OrderDetailDatasource(this);
        orderDetailDatasource.open();
    }

    private void setControl() {
        order_recycle_view = findViewById(R.id.order_recycle_view);
    }

    private void setEvent() {

    }
}