package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.siki.R;

public class MenuProduct_ProductCategoryActivity extends AppCompatActivity {
    Button btnSp, btnLoaiSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product_product_category);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btnSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

        btnLoaiSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btnSp = findViewById(R.id.btnSp);
        btnLoaiSp = findViewById(R.id.btnLoaiSp);
    }
}