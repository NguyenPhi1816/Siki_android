package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.R;
import com.example.siki.model.Account;
import com.example.siki.model.User;
import com.example.siki.otp.EmailManager;
import com.example.siki.otp.OTPManager;
import com.example.siki.otp.TimeCounter;
import com.example.siki.service.UserService;
import com.example.siki.variable.GlobalVariable;

public class OtpActivity extends AppCompatActivity {
    private User newUser;
    private Account newAccount;
    private Button sendNewOTPBtn;
    private Button validateOtpBtn;
    private Button otpToSignUpBtn;
    private EditText otpEditText;
    private TextView timerTextView;
    private TimeCounter timeCounter;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        userService = new UserService();

        sendNewOTPBtn = findViewById(R.id.send_new_otp);
        validateOtpBtn = findViewById(R.id.validate_otp_btn);
        otpToSignUpBtn = findViewById(R.id.otp_to_sign_up_btn);
        otpEditText = findViewById(R.id.otp_edit_text);
        timerTextView = findViewById(R.id.time_counter);
        timeCounter = new TimeCounter(timerTextView);

        sendOTP();

        sendNewOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP();
            }
        });

        validateOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateOTP();
            }
        });

        otpToSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(OtpActivity.this, SignUpActivity.class);
                OtpActivity.this.startActivity(activityChangeIntent);
            }
        });
    }

    private void getUserInfor() {
        Intent intent = getIntent();
        if(intent != null) {
            User newUser = (User) intent.getSerializableExtra("newUser");
            Account newAccount = (Account) intent.getSerializableExtra("newAccount");
            System.out.println(newUser);
            System.out.println(newAccount);
            if(newUser != null && newAccount != null) {
                System.out.println("get user infor successfully.");
                this.newUser = newUser;
                this.newAccount = newAccount;
            }else {
                System.out.println("get user infor failed.");
            }
        } else {
            System.out.println("get intent failed.");
        }
    }

    private void sendOTP () {
        getUserInfor();
        if (newUser != null && newAccount != null) {
            String userId = newUser.getPhoneNumber();
            String otp = OTPManager
                    .generateOTP(userId);
            System.out.println("OTP generated: " + otp);

            new EmailManager().execute(newUser.getEmail(), otp);
            timeCounter.reset();
            timeCounter.start();
        }

    }

    private void validateOTP(){
        final String otp = otpEditText.getText().toString();
        String userId = newUser.getPhoneNumber();
        boolean isValid = OTPManager.validateOTP(userId, otp);
        if (isValid) {
            boolean isSuccess = userService.insertUserInforToDB(this, newUser, newAccount);
            if(isSuccess) {
                GlobalVariable globalVariable = (GlobalVariable) getApplication();
                globalVariable.setAuthenticationInfor(newUser, true);
                Intent activityChangeIntent = new Intent(OtpActivity.this, MainActivity.class);
                OtpActivity.this.startActivity(activityChangeIntent);
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Có lỗi xảy ra trong quá trình đăng ký", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(OtpActivity.this, "OTP invalid: ", Toast.LENGTH_SHORT).show();
        }
    }
}