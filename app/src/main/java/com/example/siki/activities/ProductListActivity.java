package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.siki.R;
import com.example.siki.Adapter.ProductAdapter;
import com.example.siki.database.DatabaseProduct;
import com.example.siki.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();
    private ListView listViewProduct;
    private ProductAdapter productAdapter;
    DatabaseProduct databaseProduct;
    Button btnThem, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setControl();
        setEvent();

    }

    private void setEvent() {
        databaseProduct = new DatabaseProduct(this);
        productAdapter = new ProductAdapter(this, productList);
        listViewProduct = findViewById(R.id.product_lv);
        listViewProduct.setAdapter(productAdapter);
        btnThem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, ProductAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readDb();
    }

    private void readDb() {
        productList.clear();
        productList.addAll(databaseProduct.readDb());
        productAdapter.notifyDataSetChanged();
    }

    private void setControl() {
        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);
    }


}