package com.example.siki;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
    }
}