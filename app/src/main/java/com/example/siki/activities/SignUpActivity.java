package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.siki.R;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final Button SignUpToLoginBtn = (Button) findViewById(R.id.sign_up_to_login_btn);
        SignUpToLoginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                SignUpActivity.this.startActivity(activityChangeIntent);
            }
        });

        TextView txtDate = (TextView) findViewById(R.id.date_of_birth);
        Button search = (Button) findViewById(R.id.show_date_picker_btn);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Process to get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(SignUpActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                txtDate.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }
}