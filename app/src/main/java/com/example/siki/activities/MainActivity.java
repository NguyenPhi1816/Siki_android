package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.siki.R;
import com.example.siki.database.SikiDatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private SikiDatabaseHelper sikiDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();

        final Button loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });

        final Button registerBtn = (Button) findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, SignUpActivity.class);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB () {
        sikiDatabaseHelper = new SikiDatabaseHelper(this);
        sikiDatabaseHelper.getReadableDatabase();
    }

    private void closeDB () {
        sikiDatabaseHelper.close();
    }
}