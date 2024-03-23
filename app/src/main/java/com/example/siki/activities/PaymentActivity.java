package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.siki.Adapter.PaymentRecycleAdapter;
import com.example.siki.R;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.model.Store;
import com.example.siki.utils.PriceFormatter;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    private List<Cart> selectingCarts = new ArrayList<>() ;
    private TextView paymentTotal;
    private RecyclerView paymentRecycle;
    private PaymentRecycleAdapter paymentRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setControl();
        setEvent();
    }

    private void setEvent() {
        paymentTotal.setText(getTotalPricePayment());
        paymentRecycleAdapter = new PaymentRecycleAdapter(selectingCarts);
        paymentRecycle.setAdapter(paymentRecycleAdapter);
        paymentRecycle.setLayoutManager(new GridLayoutManager(this, 1));
    }


    private String getTotalPricePayment() {
        if (selectingCarts.size() > 0) {
            double total = selectingCarts.stream()
                    .mapToDouble(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                    .reduce(0.0, (accumulator, price) -> accumulator + price);
            return PriceFormatter.formatDouble(total);
        }
        return "";
    }

    private void setControl () {
        paymentRecycle = findViewById(R.id.rv_shopItem);
        paymentTotal = findViewById(R.id.tv_payment_total);
        Intent intent = getIntent();
        Bundle selectingCartBundle = intent.getBundleExtra("selectingCarts");
        assert selectingCartBundle != null;
        selectingCarts = (List<Cart>) selectingCartBundle.getSerializable("selectingCarts");

    }

}