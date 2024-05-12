package com.example.siki.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.siki.R;
import com.example.siki.database.OrderDataSource;
import com.example.siki.enums.OrderStatus;
import com.example.siki.model.Cart;
import com.example.siki.model.Order;
import com.example.siki.model.OrderDetail;
import com.example.siki.utils.DateFormatter;
import com.example.siki.utils.PriceFormatter;
import com.saadahmedev.popupdialog.PopupDialog;
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener;

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


    private Button btn_order_save, btn_order_back_to_order_detail, btn_order_edit_back_order_management;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_edit);
        setControl();
        setEvent();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEvent() {
        if (order != null) {
            ed_order_edit_receiverName.setText(order.getReceiverName());
            ed_order_edit_receiverPhoneNumber.setText(order.getReceiverPhoneNumber());
            ed_order_edit_receiverAddress.setText(order.getReceiverAddress());
            ed_order_edit_note.setText(order.getNote());
            ed_order_edit_created_at.setText(DateFormatter.formatLocalDateTimeToString(order.getCreatedAt()));
            ed_order_edit_totalPrice.setText(PriceFormatter.formatDouble(getTotalPriceByOrder(order)));
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
                showConfirmDialog();
            }
        });

        btn_order_back_to_order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToOrderDetailPage(order.getId());
            }
        });
        btn_order_edit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderEditActivity.this, OrderManagementActivity.class);
                startActivity(intent);
            }
        });

    }
    private void redirectToOrderDetailPage(Long orderId) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("orderId", orderId);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void updateOrder(Order updateOrder) {
        orderDataSource = new OrderDataSource(this);
        orderDataSource.open();
        order.setReceiverName(ed_order_edit_receiverName.getText().toString());
        order.setReceiverPhoneNumber(ed_order_edit_receiverPhoneNumber.getText().toString());
        order.setReceiverAddress(ed_order_edit_receiverAddress.getText().toString());
        order.setStatus(OrderStatus.valueOf(spinner_order_status.getSelectedItem().toString()));
        order.setNote(ed_order_edit_note.getText().toString());
        int rowAffected = orderDataSource.updateOrder(updateOrder.getId(), order);
        if (rowAffected != -1) {
            showSuccessMessage();
        }
    }

    private void showSuccessMessage() {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createSuccessDialog()
                .setHeading("Thành công")
                .setDescription("Bạn đã đặt cập nhật thành công")
                .build(dialog -> {
                    Intent intent = new Intent(OrderEditActivity.this, OrderManagementActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                })
                .show();
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
        btn_order_edit_back_order_management = findViewById(R.id.btn_order_edit_back_order_management);
        ed_order_edit_totalPrice.setEnabled(false);
        ed_order_edit_created_at.setEnabled(false);
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

    private void showConfirmDialog () {
        PopupDialog.getInstance(this)
                .standardDialogBuilder()
                .createIOSDialog()
                .setHeading("Lưu ý")
                .setDescription("Bạn có chắc chắn muốn thực hiện không?")
                .build(new StandardDialogActionListener() {
                    @Override
                    public void onPositiveButtonClicked(Dialog dialog) {
                        updateOrder(order);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeButtonClicked(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}