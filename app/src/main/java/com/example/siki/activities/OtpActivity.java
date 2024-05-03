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

import com.example.siki.R;
import com.example.siki.enums.OTPType;
import com.example.siki.enums.Role;
import com.example.siki.model.Account;
import com.example.siki.model.User;
import com.example.siki.otp.EmailManager;
import com.example.siki.otp.OTPManager;
import com.example.siki.otp.TimeCounter;
import com.example.siki.service.AccountService;
import com.example.siki.service.UserService;
import com.example.siki.variable.GlobalVariable;

public class OtpActivity extends AppCompatActivity {
    private User newUser;
    private Account newAccount;
    private Account authAccount;
    private User authUser;
    private Account forgotPasswordAccount;
    private User forgotPasswordUser;
    private OTPType otpType;
    private Button sendNewOTPBtn;
    private Button validateOtpBtn;
//    private Button otpToSignUpBtn;
    private EditText otpEditText;
    private TextView timerTextView;
    private TimeCounter timeCounter;
    private UserService userService;
    private AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the icon for the back button (should be a left chevron)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left);  // Replace with your own icon here
        }

        userService = new UserService(this);
        accountService = new AccountService(this);

        sendNewOTPBtn = findViewById(R.id.send_new_otp);
        validateOtpBtn = findViewById(R.id.validate_otp_btn);
//        otpToSignUpBtn = findViewById(R.id.otp_to_sign_up_btn);
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

//        otpToSignUpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent activityChangeIntent = new Intent(OtpActivity.this, SignUpActivity.class);
//                OtpActivity.this.startActivity(activityChangeIntent);
//            }
//        });
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
            User newUser = (User) intent.getSerializableExtra("newUser");
            Account newAccount = (Account) intent.getSerializableExtra("newAccount");
            Account authAccount = (Account) intent.getSerializableExtra("authAccount");
            User authUser = (User) intent.getSerializableExtra("authUser");
            Account forgotPasswordAccount = (Account) intent.getSerializableExtra("forgotPasswordAccount");
            User forgotPasswordUser = (User) intent.getSerializableExtra("forgotPasswordUser");
            if(newUser != null && newAccount != null) {
                System.out.println("get user infor successfully.");
                this.newUser = newUser;
                this.newAccount = newAccount;
                otpType = OTPType.SIGN_UP;
            } else if (authAccount != null && authUser != null) {
                this.authAccount = authAccount;
                this.authUser = authUser;
                otpType = OTPType.SIGN_IN;
            } else if (forgotPasswordAccount != null && forgotPasswordUser != null) {
                this.forgotPasswordAccount = forgotPasswordAccount;
                this.forgotPasswordUser = forgotPasswordUser;
                otpType = OTPType.FORGOT_PASS;
            }else {
                System.out.println("get user infor failed.");
            }
        } else {
            System.out.println("get intent failed.");
        }
    }

    private void sendOTP () {
        getInfor();
        String userId = "";
        String email = "";
        if (newUser != null && newAccount != null) {
            userId = newUser.getPhoneNumber();
            email = newUser.getEmail();
        } else if (authAccount != null && authUser != null) {
            userId = authAccount.getPhoneNumber();
            email = authUser.getEmail();
        } else if (forgotPasswordUser != null && forgotPasswordAccount != null) {
            userId = forgotPasswordUser.getPhoneNumber();
            email = forgotPasswordUser.getEmail();
        }
        String otp = OTPManager
                .generateOTP(userId);
        System.out.println("OTP generated: " + otp);
        new EmailManager().execute(email, otp);
        timeCounter.reset();
        timeCounter.start();
    }

    private void validateOTP(){
        final String otp = otpEditText.getText().toString();
        String userId = "";
        if (newUser != null) {
            userId = newUser.getPhoneNumber();
        } else if (authUser != null) {
            userId = authUser.getPhoneNumber();
        } else if (forgotPasswordUser != null) {
            userId = forgotPasswordUser.getPhoneNumber();
        }
        boolean isValid = OTPManager.validateOTP(userId, otp);
        if (isValid) {
            switch(otpType) {
                case SIGN_UP:{
                    boolean isSuccess = insertUserInforToDB(newUser, newAccount);
                    if(isSuccess) {
                        Intent activityChangeIntent = new Intent(OtpActivity.this, LoginActivity.class);
                        OtpActivity.this.startActivity(activityChangeIntent);
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Có lỗi xảy ra trong quá trình đăng ký", Toast.LENGTH_SHORT).show();
                    }
                    newAccount = null;
                    newUser = null;
                    break;
                }
                case SIGN_IN:{
                    GlobalVariable globalVariable = (GlobalVariable) getApplication();
                    globalVariable.setAuthenticationInfor(authUser, true);
                    Toast.makeText(this,  "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    if (authAccount.getRole().equals(String.valueOf(Role.USER))) {
                        Intent activityChangeIntent = new Intent(this, HomeActivity.class);
                        this.startActivity(activityChangeIntent);
                    } else if (authAccount.getRole().equals(String.valueOf(Role.ADMIN))) {
                        Intent activityChangeIntent = new Intent(this, MenuProduct_ProductCategoryActivity.class);
                        this.startActivity(activityChangeIntent);
                    }
                    authAccount = null;
                    authUser = null;
                    break;
                }
                case FORGOT_PASS:{
                    Intent intent = new Intent(this, ChangePasswordActivity.class);
                    intent.putExtra("forgotPasswordAccount", forgotPasswordAccount);
                    startActivity(intent);
                    forgotPasswordUser = null;
                    forgotPasswordAccount = null;
                    break;
                }
                default: {
                    break;
                }
            }

        }
        else {
            Toast.makeText(OtpActivity.this, "OTP invalid: ", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean insertUserInforToDB(User user, Account account) {
        final User retreivedUser = userService.insertUserToDB(user);
        System.out.println(retreivedUser);
        if(retreivedUser != null) {
            final Account retreivedAccount = accountService.insertAccountToDB(account);
            return retreivedAccount != null;
        }
        return false;
    }
}