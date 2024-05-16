package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.siki.R;

public class MenuProduct_ProductCategoryActivity extends AppCompatActivity {
    Button homeBtn, productBtn, productTypeBtn, sellBtn, revenueBtn, promotionBtn, btn_order_management;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product_product_category);
        setControl();
        setEvent();
    }

    private void setEvent() {
        btn_order_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, OrderManagementActivity.class);
                startActivity(intent);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        productBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, ProductListActivity.class);
                startActivity(intent);
            }
        });
        productTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });

        sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, ProductSellChartActivity.class);
                startActivity(intent);
            }
        });

        revenueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, ProductRevenueChartActivity.class);
                startActivity(intent);
            }
        });

        promotionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuProduct_ProductCategoryActivity.this, PromotionListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        btn_order_management = findViewById(R.id.btn_order_management);
        homeBtn = findViewById(R.id.home_btn);
        productBtn = findViewById(R.id.product_btn);
        productTypeBtn = findViewById(R.id.product_type_btn);
        sellBtn = findViewById(R.id.sell_btn);
        revenueBtn = findViewById(R.id.revenue_btn);
        promotionBtn = findViewById(R.id.promotion_btn);
    }
}