package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.siki.R;

public class MenuProduct_ProductCategoryActivity extends AppCompatActivity {
    CardView cvSp, cvLoaiSp;
    ImageButton btnLoaiSp, btnSp, btnSellChart, btnRevenueChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product_product_category);
        setControl();
        setEvent();
    }

    private void setEvent() {
        cvSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });
        btnSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });

        cvLoaiSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, CategoryListActivity.class);
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

        btnSellChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, ProductSellChartActivity.class);
                startActivity(intent);
            }
        });

        btnRevenueChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, ProductRevenueChartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        cvLoaiSp = findViewById(R.id.cvLoaiSp);
        cvSp = findViewById(R.id.cvSp);
        btnLoaiSp = findViewById(R.id.btnLoaiSp);
        btnSp = findViewById(R.id.btnSp);
        btnSellChart = findViewById(R.id.btnSellChart);
        btnRevenueChart = findViewById(R.id.btnRevenueChart);
    }
}