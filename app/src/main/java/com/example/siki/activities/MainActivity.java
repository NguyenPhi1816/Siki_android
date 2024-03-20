package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.siki.R;
import com.example.siki.database.SikiDatabaseHelper;
import com.example.siki.model.User;
import com.example.siki.otp.EmailManager;
import com.example.siki.otp.OTPManager;
import com.example.siki.variable.GlobalVariable;

public class MainActivity extends AppCompatActivity {
    private SikiDatabaseHelper sikiDatabaseHelper;
    private TextView authInforTextView;
    private LinearLayout authenticatedLayout;
    private LinearLayout unauthenticatedLayout;
    private Button logoutButton;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        authInforTextView = findViewById(R.id.auth_infor);
        authenticatedLayout = findViewById(R.id.authenticated_layout);
        unauthenticatedLayout = findViewById(R.id.unauthenticated_layout);
        logoutButton = findViewById(R.id.logout_btn);
        loginBtn = (Button) findViewById(R.id.login_btn);
        registerBtn = (Button) findViewById(R.id.register_btn);

        openDB();
        getAuthenticationInfor();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToLoginActivity();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                navigateToSignUpActivity();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logout();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void navigateToLoginActivity(){
        Intent activityChangeIntent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(activityChangeIntent);
    }

    private void navigateToSignUpActivity(){
        Intent activityChangeIntent = new Intent(MainActivity.this, SignUpActivity.class);
        MainActivity.this.startActivity(activityChangeIntent);
    }

    private void openDB () {
        sikiDatabaseHelper = new SikiDatabaseHelper(this);
        sikiDatabaseHelper.getReadableDatabase();
    }

    private void closeDB () {
        sikiDatabaseHelper.close();
    }

    private void logout() {
        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        globalVariable.setAuthenticationInfor(null, false);
        getAuthenticationInfor();
    }

    private void getAuthenticationInfor() {
        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        boolean isLoggedIn = globalVariable.isLoggedIn();
        if (isLoggedIn){
            User authUser = globalVariable.getAuthUser();
            authInforTextView.setText("Username: " + authUser.getFirstName() + " " + authUser.getLastName());
            authenticatedLayout.setVisibility(View.VISIBLE);
            unauthenticatedLayout.setVisibility(View.GONE);
        } else {
            authInforTextView.setText("Đăng nhập để tiếp tục sử dụng");
            unauthenticatedLayout.setVisibility(View.VISIBLE);
            authenticatedLayout.setVisibility(View.GONE);
        }
    }
}