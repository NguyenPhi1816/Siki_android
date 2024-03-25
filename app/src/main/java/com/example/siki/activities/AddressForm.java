package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.siki.R;
import com.example.siki.model.Cart;
import com.example.siki.model.User;

import java.util.List;

public class AddressForm extends AppCompatActivity {

    private EditText ed_address_ho, ed_address_ten, ed_address_diachi, ed_address_sdt;
    private Button btn_address_edit, btn_address_cancel, btn_back_to_payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btn_address_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressForm.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        btn_back_to_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressForm.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        ed_address_ho = findViewById(R.id.ed_address_ho);
        ed_address_ten = findViewById(R.id.ed_address_ten);
        ed_address_diachi = findViewById(R.id.ed_address_diachi);
        ed_address_sdt = findViewById(R.id.ed_address_sdt);

        btn_address_edit = findViewById(R.id.btn_address_edit);
        btn_address_cancel = findViewById(R.id.btn_address_cancel);
        btn_back_to_payment = findViewById(R.id.btn_back_to_payment);
        Intent intent = getIntent();
        Bundle userBundle = intent.getBundleExtra("user");
        assert userBundle != null;
        User currentUser = (User) userBundle.getSerializable("user");
        ed_address_diachi.setText(currentUser.getAddress());
        ed_address_ho.setText(currentUser.getFirstName());
        ed_address_ten.setText(currentUser.getLastName());
        ed_address_sdt.setText(currentUser.getPhoneNumber());
    }
}