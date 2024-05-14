package com.example.siki.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.siki.Adapter.OrderRecycleAdapter;
import com.example.siki.R;
import com.example.siki.database.OrderDataSource;
import com.example.siki.database.OrderDetailDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.enums.OrderStatus;
import com.example.siki.model.Order;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderManagementActivity extends AppCompatActivity {
    private RecyclerView order_recycle_view;
    private List<Order> orders = new ArrayList<>();

    private List<Order> data_all = new ArrayList<>();
    private final String pending = "PENDING";
    private final String all = "ALL";
    private final String shipping = "SHIPPING";
    private final String success = "SUCCESS";
    private String currentStatus = all;

    private UserDataSource userDataSource ;
    private ProductDatabase productDatabase;
    private OrderDataSource orderDataSource;
    private OrderDetailDatasource orderDetailDatasource;

    private OrderRecycleAdapter orderRecycleAdapter;
    private Button btn_filter_date;
    private SearchView sv_order;
    final Calendar c = Calendar.getInstance();
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);


    private TextView tv_order_status_all, tv_order_status_pending, tv_order_status_shipping, tv_order_status_success;

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
        List<Order> orderList = orderDataSource.findAllByOrderStatus(userDataSource, productDatabase, orderDetailDatasource, currentStatus);
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
        tv_order_status_all = findViewById(R.id.tv_order_status_all);
        tv_order_status_pending = findViewById(R.id.tv_order_status_pending);
        tv_order_status_shipping = findViewById(R.id.tv_order_status_shipping);
        tv_order_status_success = findViewById(R.id.tv_order_status_success);

    }

    @SuppressLint("ResourceAsColor")
    private void setEvent() {

        btn_filter_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateOfBirth();
            }
        });
        tv_order_status_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetOrderStatusTextView();
                tv_order_status_all.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main_color));
                currentStatus = all;
                readDb();
            }
        });
        tv_order_status_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetOrderStatusTextView();
                tv_order_status_pending.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main_color));
                currentStatus = OrderStatus.Pending.toString();
                readDb();
            }
        });
        tv_order_status_shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetOrderStatusTextView();
                tv_order_status_shipping.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main_color));
                currentStatus = OrderStatus.Shipping.toString();
                readDb();
            }
        });
        tv_order_status_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetOrderStatusTextView();
                tv_order_status_success.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.main_color));
                currentStatus = OrderStatus.Success.toString();
                readDb();
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

    @SuppressLint("ResourceAsColor")
    private void resetOrderStatusTextView () {

        tv_order_status_all.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        tv_order_status_pending.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        tv_order_status_shipping.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
        tv_order_status_success.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
    }

    private void setDateOfBirth() {
        // Process to get Current Date

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(OrderManagementActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        orders.clear();
                        for (Order order : data_all) {
                            int newMonth = monthOfYear + 1;
                            if (order.getCreatedAt().getMonthValue() == (newMonth)
                                && order.getCreatedAt().getDayOfMonth() == dayOfMonth
                                && order.getCreatedAt().getYear() == year) {
                                orders.add(order);
                            }
                         /*   System.out.println((monthOfYear + 1) + " " + order.getCreatedAt().getMonthValue() + "");
                            System.out.println(dayOfMonth + " " + order.getCreatedAt().getDayOfMonth());
                            System.out.println(year + " " + order.getCreatedAt().getYear());
                            orders.add(order);*/
                        }
                        orderRecycleAdapter.notifyDataSetChanged();
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }
}