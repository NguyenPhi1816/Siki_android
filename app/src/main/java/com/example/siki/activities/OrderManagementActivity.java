package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.siki.Adapter.OrderRecycleAdapter;
import com.example.siki.R;
import com.example.siki.database.OrderDataSource;
import com.example.siki.database.OrderDetailDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Order;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderManagementActivity extends AppCompatActivity {
    private RecyclerView order_recycle_view;
    private List<Order> orders = new ArrayList<>();

    private List<Order> data_all = new ArrayList<>();

    private UserDataSource userDataSource ;
    private ProductDatabase productDatabase;
    private OrderDataSource orderDataSource;
    private OrderDetailDatasource orderDetailDatasource;

    private OrderRecycleAdapter orderRecycleAdapter;
    private Button btn_filter_date;

   private SearchView sv_order;

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

        orders.clear();
        List<Order> orderList = orderDataSource.findAll(userDataSource, productDatabase, orderDetailDatasource);
        orders.addAll(orderList);
        data_all.addAll(orderList);
        orderRecycleAdapter = new OrderRecycleAdapter(orders, this);
        order_recycle_view.setAdapter(orderRecycleAdapter);
        orderRecycleAdapter.notifyDataSetChanged();
    }

    private void setControl() {
        sv_order = findViewById(R.id.sv_order);
        order_recycle_view = findViewById(R.id.order_recycle_view);
        btn_filter_date = findViewById(R.id.btn_filter_date);
    }

    private void setEvent() {

        btn_filter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Pair<Long, Long>> materialDatePicker = MaterialDatePicker.Builder.dateRangePicker()
                        .setSelection(new Pair<>(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds()))
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {

                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "Tag");
            }
        });
         sv_order.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                orders.clear();
                if (newText.equals("")) {
                    orders.addAll(data_all);

                } else {
                    for (Order order : data_all) {
                        if (order.getId().toString().contains(newText)) {
                            orders.add(order);
                        }
                    }
                }
                orderRecycleAdapter.notifyDataSetChanged();
                return false;
            }
        });



        orderRecycleAdapter = new OrderRecycleAdapter(orders, this);
        order_recycle_view.setAdapter(orderRecycleAdapter);
        order_recycle_view.setLayoutManager(new GridLayoutManager(this, 1));
    }
}