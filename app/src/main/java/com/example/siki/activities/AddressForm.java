package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.siki.R;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.User;


public class AddressForm extends AppCompatActivity {

    private EditText ed_address_ho, ed_address_ten, ed_address_diachi, ed_address_sdt;
    private Button btn_address_edit, btn_address_cancel, btn_back_to_payment;
    private User currentUser = new User();
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
        btn_address_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDataSource userDataSource = new UserDataSource(getApplicationContext());
                userDataSource.open();
                currentUser.setFirstName(ed_address_ho.getText().toString().trim());
                currentUser.setLastName(ed_address_ten.getText().toString().trim());
                currentUser.setAddress(ed_address_diachi.getText().toString().trim());
                currentUser.setPhoneNumber(ed_address_sdt.getText().toString().trim());
                int checked = userDataSource.updateUser(currentUser);
                if (checked != -1) {

                    showSuccessMessage();
                } else {
                    //
                }
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
        if (userBundle != null) {
            currentUser = (User) userBundle.getSerializable("user");
            ed_address_diachi.setText(currentUser.getAddress());
            ed_address_ho.setText(currentUser.getFirstName());
            ed_address_ten.setText(currentUser.getLastName());
            ed_address_sdt.setText(currentUser.getPhoneNumber());
        }
    }
    private void showSuccessMessage() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_success_dialog);
        Button btn_payment_success = dialog.findViewById(R.id.btn_payment_success);
        TextView tv_title = dialog.findViewById(R.id.tv_title);
        tv_title.setText("Cap nhat thanh cong");
        btn_payment_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressForm.this, PaymentActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}