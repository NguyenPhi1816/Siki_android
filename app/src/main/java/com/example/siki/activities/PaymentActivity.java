package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.siki.Adapter.PaymentRecycleAdapter;
import com.example.siki.R;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Cart;
import com.example.siki.model.User;
import com.example.siki.utils.PriceFormatter;
import com.example.siki.variable.GlobalVariable;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    private List<Cart> selectingCarts = new ArrayList<>() ;
    private TextView paymentTotal, tv_payment_userAddress;
    private final String userAddressFormat = "%s - %s %s";

    private Spinner spinner_paymentType;
    private RecyclerView paymentRecycle;
    private ImageView iv_edit_address;
    private Button btn_back_to_cart;

    private GlobalVariable globalVariable = new GlobalVariable();

    private UserDataSource userDataSource;
    private PaymentRecycleAdapter paymentRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setControl();
        setEvent();
    }

    private void setEvent() {
        userDataSource = new UserDataSource(this);
        userDataSource.open();
        paymentTotal.setText(getTotalPricePayment());
        btn_back_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        iv_edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentActivity.this, AddressForm.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", globalVariable.getAuthUser());
                intent.putExtra("user", bundle);
                startActivity(intent);
            }
        });

        paymentRecycleAdapter = new PaymentRecycleAdapter(selectingCarts);
        paymentRecycle.setAdapter(paymentRecycleAdapter);
        paymentRecycle.setLayoutManager(new GridLayoutManager(this, 1));
    }


    @Override
    protected void onResume() {
        super.onResume();
        getUserAddress();
    }

    private void getUserAddress() {
        userDataSource = new UserDataSource(this);
        userDataSource.open();
        User user = userDataSource.getUserById(1);
        globalVariable.setAuthUser(user);

        if (globalVariable.getAuthUser() != null) {
            User currentUser = globalVariable.getAuthUser();
            String ho = currentUser.getFirstName();
            String ten = currentUser.getLastName();
            String address = currentUser.getAddress();
            String sdt = currentUser.getPhoneNumber();

            String fullName = ho.concat(" ").concat(ten);
            String userAddress = String.format(userAddressFormat, fullName, sdt, address);
            tv_payment_userAddress.setText(userAddress);
        }
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
        spinner_paymentType = findViewById(R.id.spinner_paymentType);
        btn_back_to_cart = findViewById(R.id.btn_back_to_cart);
        paymentRecycle = findViewById(R.id.rv_shopItem);
        paymentTotal = findViewById(R.id.tv_payment_total);
        iv_edit_address = findViewById(R.id.iv_edit_address);
        tv_payment_userAddress = findViewById(R.id.tv_payment_userAddress);
        Intent intent = getIntent();
        Bundle selectingCartBundle = intent.getBundleExtra("selectingCarts");
        assert selectingCartBundle != null;
        selectingCarts = (List<Cart>) selectingCartBundle.getSerializable("selectingCarts");
    }

}