package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.siki.Adapter.ProductListForCustomerRecycleAdapter;
import com.example.siki.R;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ListProductForCustomerActivity extends AppCompatActivity {
    private List<Product> productList = new ArrayList<>();
    TextView tv_categoryName;
    RecyclerView rv_product_list_customer;

    ProductListForCustomerRecycleAdapter productListForCustomerRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_for_customer);
        init();
        setControl();
        tv_categoryName.setText("Dien thoai");
        productListForCustomerRecycleAdapter = new ProductListForCustomerRecycleAdapter(productList, this);
        rv_product_list_customer.setAdapter(productListForCustomerRecycleAdapter);
        rv_product_list_customer.setLayoutManager(new GridLayoutManager(this, 2));

    }

    private void setControl() {
        tv_categoryName = findViewById(R.id.tv_categoryName);
        rv_product_list_customer = findViewById(R.id.rv_product_list_customer);
    }

    private void init() {
        Product product1 = new Product();
        product1.setName("Samsung galaxy");

        Product product2 = new Product();
        product2.setName("Samsung galaxy");

        Product product3 = new Product();
        product3.setName("Samsung galaxy");

        Product product4 = new Product();
        product4.setName("Samsung galaxy");

        Product product5 = new Product();
        product5.setName("Samsung galaxy");

        Product product6 = new Product();
        product6.setName("Samsung galaxy");

        Product product7 = new Product();
        product7.setName("Samsung galaxy");

        Product product8 = new Product();
        product8.setName("Samsung galaxy");

        Product product9 = new Product();
        product9.setName("Samsung galaxy");
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);
        productList.add(product6);
        productList.add(product7);
        productList.add(product8);
        productList.add(product9);
    }
}