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

import com.example.siki.API.AuthApi;
import com.example.siki.API.UserApi;
import com.example.siki.Bcrypt.BcryptManager;
import com.example.siki.R;
import com.example.siki.database.AccountDataSource;
import com.example.siki.database.SikiDatabaseHelper;
import com.example.siki.database.UserDataSource;
import com.example.siki.dto.auth.LoginDto;
import com.example.siki.dto.user.UserProfile;
import com.example.siki.enums.Role;
import com.example.siki.model.Account;
import com.example.siki.model.User;
import com.example.siki.service.UserService;
import com.example.siki.utils.AuthHeader;
import com.example.siki.utils.Constants;
import com.example.siki.variable.GlobalVariable;

import org.mindrot.jbcrypt.BCrypt;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            String SIKI_CLIENT_ID = Constants.AuthVariable.SIKI_CLIENT_ID;

            String GRANT_TYPE = Constants.AuthVariable.GRANT_TYPE;

            String CLIENT_SECRET = Constants.AuthVariable.CLIENT_SECRET;

            String SCOPE = Constants.AuthVariable.SCOPE;
            AuthApi.authApi.login(SIKI_CLIENT_ID, GRANT_TYPE, CLIENT_SECRET, phoneNumber_str, pass_str, SCOPE).enqueue(new Callback<LoginDto>() {
                @Override
                public void onResponse(Call<LoginDto> call, Response<LoginDto> response) {
                    LoginDto loginDto = response.body();
                    System.out.println(loginDto.getAccess_token());

                    String access_token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJMaHVxM0hRc0RiU01lUVZJR25Ub0Q3U0NGaEJQUS1LTDlramtGZHhZMFNFIn0.eyJleHAiOjE3MTc5NTA4OTcsImlhdCI6MTcxNzk0OTM5NywianRpIjoiNjVkMDcyODktM2JhZC00Yzc3LWJjNWItNzhmZWIzYTMwMzhlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4ODgwL3JlYWxtcy9zaWtpIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjNlYjAzOWE5LTM5ZTUtNGZmNC1hNjE4LTRhZjM5MDY3Y2UyZCIsInR5cCI6IkJlYXJlciIsImF6cCI6InNpa2ktY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6IjQ2ZjY4MzgxLWM4ZjItNDZkYS05ZGI2LTFhMDVjMzI4ODYzZiIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiaHR0cDovL2xvY2FsaG9zdDozMDAwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsIkNVU1RPTUVSIiwiZGVmYXVsdC1yb2xlcy1zaWtpIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im1pY3JvcHJvZmlsZS1qd3QgcHJvZmlsZSBlbWFpbCIsInNpZCI6IjQ2ZjY4MzgxLWM4ZjItNDZkYS05ZGI2LTFhMDVjMzI4ODYzZiIsInVwbiI6InRlc3QxMDBAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiYWEgYmIiLCJncm91cHMiOlsib2ZmbGluZV9hY2Nlc3MiLCJDVVNUT01FUiIsImRlZmF1bHQtcm9sZXMtc2lraSIsInVtYV9hdXRob3JpemF0aW9uIl0sInByZWZlcnJlZF91c2VybmFtZSI6InRlc3QxMDBAZ21haWwuY29tIiwiZ2l2ZW5fbmFtZSI6ImFhIiwiZmFtaWx5X25hbWUiOiJiYiIsImVtYWlsIjoidGVzdDEwMEBnbWFpbC5jb20ifQ.TANSroVvLxur3gPxuBV4h1jwpxt9zMEXns--Db-HSlIcbvEJdMwFz3cj4kuaE6ogz4TvGPtnACCc-XrLFcFZcD-CVZUwBCObt3WnxCqJAhBHerSDj8chFpKCvF4S69_8IOo71VYUrlwlIHhLpkd2C40qZJJMVuPm3uCrrdP9stQMmv56LJ16E6Ef23ugz7Aa75oCDJokTovnBA6n-nzUmaR_hUaqECxKWmc0LVvy2HB2VqRNNA1apamTjSmK_OFD6M6KeHTcmBS5R5TfszCCyXnuIwilLXHl2Br4BtwpvI3jJRshPu8DgrtbHUcJo7QQ1Vwv0ClCCkm_nDYrfPD3Vg";
                    String authHeader = AuthHeader.convertAuthHeaderFromAccessToken(access_token);
                    System.out.println(authHeader);
                    UserApi.userApi.getUserProfile(authHeader).enqueue(new Callback<UserProfile>() {
                        @Override
                        public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                            UserProfile userProfile = response.body();
                            User authUser = new User(userProfile);
                            GlobalVariable globalVariable = (GlobalVariable) getApplication();
                            globalVariable.setAuthenticationInfor(authUser, true, authHeader);
                            Toast.makeText(getApplicationContext(),  "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            redirectToHomePage();
                        }

                        @Override
                        public void onFailure(Call<UserProfile> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                            Toast.makeText(getApplicationContext(), "Có lỗi xảy ra trong quá trình đăng ký", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<LoginDto> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                }
            });


            /*Account account = getAccountByCredentials(phoneNumber_str, pass_str);
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
            }*/
        }
    }

    private void redirectToHomePage() {
        Intent activityChangeIntent = new Intent(this, HomeActivity.class);
        this.startActivity(activityChangeIntent);
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