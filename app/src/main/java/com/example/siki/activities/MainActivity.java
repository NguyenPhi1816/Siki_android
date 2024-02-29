package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.siki.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.login_btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });
    }
}