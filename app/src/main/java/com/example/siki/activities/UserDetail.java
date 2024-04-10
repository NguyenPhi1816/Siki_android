package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.siki.R;

public class UserDetail extends AppCompatActivity {
    Button btnBack, btnCart, btnHoVaTen, btnNickname, btnNgaySinh, btnGioiTinh, btnQuocTich, btnSDT, btnEmail, btnDoiMK;
    TextView tvHoVaTen, tvNickName, tvNgaySinh, tvGioiTinh, tvQuocTich, tvSDT, tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        setControl();
    }

    private void setControl() {
        btnBack = findViewById(R.id.btnBack);
        btnCart = findViewById(R.id.btnCart);
        btnHoVaTen = findViewById(R.id.btnHoVaTen);
        btnNickname = findViewById(R.id.btnNickname);
        btnNgaySinh = findViewById(R.id.btnNgaySinh);
        btnGioiTinh = findViewById(R.id.btnGioiTinh);
        btnQuocTich = findViewById(R.id.btnQuocTich);
        btnSDT = findViewById(R.id.btnSDT);
        btnEmail = findViewById(R.id.btnEmail);
        btnDoiMK = findViewById(R.id.btnDoiMK);

        tvHoVaTen = findViewById(R.id.tvHoVaTen);
        tvNickName = findViewById(R.id.tvNickname);
        tvNgaySinh = findViewById(R.id.tvNgaySinh);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvQuocTich = findViewById(R.id.tvQuocTich);
        tvSDT = findViewById(R.id.tvSDT);
        tvEmail = findViewById(R.id.tvEmail);
    }
}