package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.siki.Adapter.ProductListForCustomerRecycleAdapter;
import com.example.siki.R;
import com.example.siki.database.ProductCategoryDatabase;
import com.example.siki.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListProductForCustomerActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();
    TextView tv_categoryName;
    RecyclerView rv_product_list_customer;

    private ProductCategoryDatabase productCategoryDatabase;

    private final Integer categoryId = 1;

    private ProductListForCustomerRecycleAdapter productListForCustomerRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_for_customer);
        setControl();
        tv_categoryName.setText("Dien thoai");
        productListForCustomerRecycleAdapter = new ProductListForCustomerRecycleAdapter(productList, this);
        rv_product_list_customer.setAdapter(productListForCustomerRecycleAdapter);
        rv_product_list_customer.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void onResume() {
        super.onResume();
        readDb();
    }

    private void readDb() {
        // get list product by category id
        productCategoryDatabase = new ProductCategoryDatabase(this);
        productCategoryDatabase.open();
        productList.clear();
        productList.addAll(productCategoryDatabase.findByCategoryId(categoryId));
        productListForCustomerRecycleAdapter.notifyDataSetChanged();
    }

    private void setControl() {
        tv_categoryName = findViewById(R.id.tv_categoryName);
        rv_product_list_customer = findViewById(R.id.rv_product_list_customer);
    }
}