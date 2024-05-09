package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.siki.R;
import com.example.siki.model.Order;
import com.google.android.material.search.SearchView;

import java.util.ArrayList;
import java.util.List;

public class OrderManagementActivity extends AppCompatActivity {
    SearchView order_search_view;
    RecyclerView order_recycle_view;

    List<Order> orders = new ArrayList<>();

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
    }

    private void setControl() {
        order_search_view = findViewById(R.id.order_search_view);
        order_recycle_view = findViewById(R.id.order_recycle_view);
    }

    private void setEvent() {

    }
}