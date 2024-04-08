package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.Bcrypt.BcryptManager;
import com.example.siki.R;
import com.example.siki.database.AccountDataSource;
import com.example.siki.database.SikiDatabaseHelper;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Account;
import com.example.siki.model.User;
import com.example.siki.service.UserService;
import com.example.siki.variable.GlobalVariable;

import org.mindrot.jbcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private EditText password;
    private TextView phone_number_error_message;
    private TextView pass_error_message;
    private Button loginToHomeBtn;
    private Button createNewAccountBtn;
    private Button submitBtn;
    private Button forgotPasswordBtn;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the icon for the back button (should be a left chevron)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left);  // Replace with your own icon here
        }

        userService = new UserService(this);

        phoneNumber = (EditText) findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.password);
        phone_number_error_message = (TextView) findViewById(R.id.phone_number_error_message);
        pass_error_message = (TextView) findViewById(R.id.pass_error_message);
//        loginToHomeBtn = (Button) findViewById(R.id.login_to_home_btn);
        createNewAccountBtn = (Button) findViewById(R.id.create_new_account_btn);
        submitBtn = (Button) findViewById(R.id.submit_btn);
        forgotPasswordBtn = (Button) findViewById(R.id.forgot_pass_btn);

//        loginToHomeBtn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                navigateToHome();
//            }
//        });

        createNewAccountBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToSignUp();
            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToForgotPassword();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to previous one if there is any
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void navigateToHome() {
        Intent activityChangeIntent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(activityChangeIntent);
    }

    public void navigateToSignUp() {
        Intent activityChangeIntent = new Intent(LoginActivity.this, SignUpActivity.class);
        LoginActivity.this.startActivity(activityChangeIntent);
    }

    public void navigateToForgotPassword() {
        Intent activityChangeIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        LoginActivity.this.startActivity(activityChangeIntent);
    }

    public void login() {
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
                User authUser = userService.getUserByPhoneNumber(account.getPhoneNumber());
                if (authUser != null) {
                    Intent intent = new Intent(this, OtpActivity.class);
                    intent.putExtra("authAccount", account);
                    intent.putExtra("authUser", authUser);
                    startActivity(intent);
                } else {
                    Toast.makeText(this,  "Có lỗi xảy ra trong quá trình đăng nhập", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this,  "Có lỗi xảy ra trong quá trình đăng nhập", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Account getAccountByCredentials (String phoneNumber, String password) {
        Context context = this;
        AccountDataSource accountDataSource = new AccountDataSource(context);
        accountDataSource.open();
        Account retrievedAccount = accountDataSource.getAccountByPhoneNumber(phoneNumber);
        if (retrievedAccount != null) {
            if (!BcryptManager.checkPassword(password, retrievedAccount.getPassword())) {
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
    };
}