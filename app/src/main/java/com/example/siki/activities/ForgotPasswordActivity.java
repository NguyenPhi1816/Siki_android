package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.siki.FormValidator.FormValidator;
import com.example.siki.R;
import com.example.siki.model.Account;
import com.example.siki.model.User;
import com.example.siki.service.AccountService;
import com.example.siki.service.UserService;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button forgotPassToLoginBtn;
    private Button sendOtpBtn;
    private EditText phoneNumberEditText;
    private TextView phoneNumberErrorMessage;
    private UserService userService;
    private AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        userService = new UserService(this);
        accountService = new AccountService(this);

        forgotPassToLoginBtn = findViewById(R.id.forgot_pass_to_login_btn);
        sendOtpBtn = findViewById(R.id.send_otp);
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        phoneNumberErrorMessage = findViewById(R.id.phone_number_error_message);

        phoneNumberErrorMessage.setVisibility(View.GONE);

        forgotPassToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToLogin();
            }
        });

        sendOtpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendOTP();
            }
        });

        phoneNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                boolean condition = phoneNumber.length() == 10 && phoneNumber.startsWith("0");
                FormValidator.editTextValidator(hasFocus, phoneNumberErrorMessage, condition, "Vui lòng nhập thông tin này");
            }
        });
    }

    public void navigateToLogin() {
        Intent activityChangeIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        ForgotPasswordActivity.this.startActivity(activityChangeIntent);
    }

    public void sendOTP() {
        String phoneNumber = phoneNumberEditText.getText().toString();
        if (phoneNumber.length() == 10 && phoneNumber.startsWith("0")){
            User forgotPasswordUser = userService.getUserByPhoneNumber(phoneNumber);
            Account forgotPasswordAccount = accountService.getAccountByPhoneNumber(phoneNumber);
            Intent intent = new Intent(this, OtpActivity.class);
            intent.putExtra("forgotPasswordUser", forgotPasswordUser);
            intent.putExtra("forgotPasswordAccount", forgotPasswordAccount);
            startActivity(intent);
        } else {
            phoneNumberErrorMessage.setText("Số điện thoại không đúng");
            phoneNumberErrorMessage.setVisibility(View.VISIBLE);
        }
    }
}