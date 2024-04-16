package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.Bcrypt.BcryptManager;
import com.example.siki.FormValidator.FormValidator;
import com.example.siki.R;
import com.example.siki.enums.OTPType;
import com.example.siki.model.Account;
import com.example.siki.model.User;
import com.example.siki.service.AccountService;

public class ChangePasswordActivity extends AppCompatActivity {
    private Account forgotPasswordAccount;
    private AccountService accountService;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private TextView newPasswordTextView;
    private TextView confirmPasswordTextView;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the icon for the back button (should be a left chevron)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left);  // Replace with your own icon here
        }

        accountService = new AccountService(this);

        newPasswordEditText = findViewById(R.id.new_password);
        confirmPasswordEditText = findViewById(R.id.confirm_new_password);
        newPasswordTextView = findViewById(R.id.new_password_error_message);
        confirmPasswordTextView = findViewById(R.id.confirm_pass_error_message);
        submitBtn = findViewById(R.id.submit_btn);

        newPasswordTextView.setVisibility(View.GONE);
        confirmPasswordTextView.setVisibility(View.GONE);

        getInfor();

        newPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                FormValidator.editTextValidator(hasFocus, newPasswordTextView, newPasswordEditText.getText().toString().isEmpty(), "Vui lòng nhập mật khẩu");
            }
        });

        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                FormValidator.editTextValidator(hasFocus, confirmPasswordTextView, confirmPasswordEditText.getText().toString().isEmpty(), "Vui lòng nhập lại mật khẩu");
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changePassword();
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

    private void getInfor() {
        Intent intent = getIntent();
        if(intent != null) {
            Account forgotPasswordAccount = (Account) intent.getSerializableExtra("forgotPasswordAccount");
            if (forgotPasswordAccount != null) {
                this.forgotPasswordAccount = forgotPasswordAccount;
            }else {
                System.out.println("get user infor failed.");
            }
        } else {
            System.out.println("get intent failed.");
        }
    }

    private void changePassword() {
        final String newPass = newPasswordEditText.getText().toString();
        final String confirmPass = confirmPasswordEditText.getText().toString();

        if (newPass.isEmpty()) {
            newPasswordTextView.setText("Vui lòng nhập mật khẩu");
            newPasswordTextView.setVisibility(View.VISIBLE);
        }

        if (confirmPass.isEmpty()) {
            confirmPasswordTextView.setText("Vui lòng nhập lại mật khẩu");
            confirmPasswordTextView.setVisibility(View.VISIBLE);
        }

        if (!newPass.isEmpty() && !confirmPass.isEmpty() && newPass.equals(confirmPass)){
            String hashedNewPassword = BcryptManager.hashPassword(newPass);
            forgotPasswordAccount.setPassword(hashedNewPassword);
            accountService.updateAccount(forgotPasswordAccount);
            Toast.makeText(this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            Intent activityChangeIntent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
            ChangePasswordActivity.this.startActivity(activityChangeIntent);
        } else if (!newPass.equals(confirmPass)) {
            confirmPasswordTextView.setText("Mật khẩu nhập lại không đúng");
            confirmPasswordTextView.setVisibility(View.VISIBLE);
        }
    }
}