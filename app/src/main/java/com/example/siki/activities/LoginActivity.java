package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.R;
import com.example.siki.database.AccountDataSource;
import com.example.siki.database.SikiDatabaseHelper;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Account;
import com.example.siki.model.User;

import org.mindrot.jbcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private EditText password;
    private TextView phone_number_error_message;
    private TextView pass_error_message;

    public Account getAccountByCredentials (String phoneNumber, String password) {
        Context context = this;
        AccountDataSource accountDataSource = new AccountDataSource(context);
        accountDataSource.open();
        Account retrievedAccount = accountDataSource.getAccountByPhoneNumber(phoneNumber);
        if (retrievedAccount != null) {
            if (!checkPassword(password, retrievedAccount.getPassword())) {
                pass_error_message.setText("Mật khẩu không đúng");
                accountDataSource.close();
                return null;
            }
            System.out.println("Retrieved Account: " + retrievedAccount.toString());
        } else {
            phone_number_error_message.setText("Số điện thoại không đúng.");
            System.out.println("Account not found.");
        }
        accountDataSource.close();
        return retrievedAccount;
    }

    // Method to check if a password matches the hashed password
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        // Check if the provided password matches the hashed password
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.password);
        phone_number_error_message = (TextView) findViewById(R.id.phone_number_error_message);
        pass_error_message = (TextView) findViewById(R.id.pass_error_message);


        final Button LoginToHomeBtn = (Button) findViewById(R.id.login_to_home_btn);
        LoginToHomeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(activityChangeIntent);
            }
        });

        final Button CreateNewAccountBtn = (Button) findViewById(R.id.create_new_account_btn);
        CreateNewAccountBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(activityChangeIntent);
            }
        });

        final Button SubmitButton = (Button) findViewById(R.id.submit_btn);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String phoneNumber_str = phoneNumber.getText().toString();
                String pass_str = password.getText().toString();

                if (phoneNumber_str.isEmpty()) {
                    phone_number_error_message.setText("Vui lòng nhập số điện thoại.");
                }

                if (pass_str.isEmpty()) {
                    pass_error_message.setText("Vui lòng nhập mật khẩu.");
                }

                if (!phoneNumber_str.isEmpty() && !pass_str.isEmpty()){
                    Account account = getAccountByCredentials(phoneNumber_str, pass_str);
                    if (account != null) {
                        Toast.makeText(LoginActivity.this,  "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        phoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    phone_number_error_message.setText("");
                }
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pass_error_message.setText("");
                }
            }
        });
    }
}