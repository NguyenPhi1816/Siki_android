package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.siki.R;
import com.example.siki.database.OrderDataSource;
import com.example.siki.model.Cart;
import com.example.siki.model.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OrderEditActivity extends AppCompatActivity {

    private EditText ed_order_edit_receiverName, ed_order_edit_receiverPhoneNumber, ed_order_edit_receiverAddress,
            ed_order_edit_note, ed_order_edit_totalPrice, ed_order_edit_created_at;
    private Spinner spinner_order_status;
    private ArrayAdapter orderStatusAdapter;
    private List<String> orderStatusTypes = new ArrayList<>();
    private Order order = new Order();

    private OrderDataSource orderDataSource ;


    private Button btn_order_save, btn_order_back_to_order_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_edit);
        setControl();
        setEvent();

    }

    private void setEvent() {
        if (order != null) {
            ed_order_edit_receiverName.setText(order.getReceiverName());
            ed_order_edit_receiverPhoneNumber.setText(order.getReceiverPhoneNumber());
            ed_order_edit_receiverAddress.setText(order.getReceiverAddress());
            ed_order_edit_note.setText(order.getNote());
            ed_order_edit_created_at.setText(order.getCreatedAt().toString());
            switch (order.getStatus()) {
                case Pending:
                    spinner_order_status.setSelection(0);
                    break;
                case Success:
                    spinner_order_status.setSelection(1);
                    break;
                case Shipping:
                    spinner_order_status.setSelection(2);
                    break;
                default:
                    break;
            }
        }
        btn_order_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.setReceiverName(ed_order_edit_receiverName.getText().toString());
                order.setReceiverName(ed_order_edit_receiverName.getText().toString());
                order.setReceiverName(ed_order_edit_receiverName.getText().toString());
                order.setReceiverName(ed_order_edit_receiverName.getText().toString());

                updateOrder(order);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void updateOrder(Order updateOrder) {
        orderDataSource = new OrderDataSource(this);
        orderDataSource.open();
        orderDataSource.updateOrder(updateOrder.getId(), order);
    }

    private void setControl() {
        ed_order_edit_receiverName = findViewById(R.id.ed_order_edit_receiverName);
        ed_order_edit_receiverPhoneNumber = findViewById(R.id.ed_order_edit_receiverPhoneNumber);
        ed_order_edit_receiverAddress = findViewById(R.id.ed_order_edit_receiverAddress);
        ed_order_edit_note = findViewById(R.id.ed_order_edit_note);
        ed_order_edit_totalPrice = findViewById(R.id.ed_order_edit_totalPrice);
        ed_order_edit_created_at = findViewById(R.id.ed_order_edit_created_at);
        spinner_order_status = findViewById(R.id.spinner_order_status);
        btn_order_save = findViewById(R.id.btn_order_save);
        btn_order_back_to_order_detail = findViewById(R.id.btn_order_back_to_order_detail);
        Intent intent = getIntent();
        Bundle newOrder = intent.getBundleExtra("order");
        if (newOrder != null) {
            order = (Order) newOrder.getSerializable("order");
        }
        initOrderStatus();
    }

    private void initOrderStatus() {
        orderStatusTypes.add("Pending");
        orderStatusTypes.add("Success");
        orderStatusTypes.add("Shipping");
        orderStatusAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, orderStatusTypes);
        spinner_order_status.setAdapter(orderStatusAdapter);
    }
}