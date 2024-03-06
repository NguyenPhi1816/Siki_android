package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.siki.R;
import com.example.siki.Adapter.ProductAdapter;
import com.example.siki.model.Product;
import com.example.siki.model.ProductPrice;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private List<Product> productList ;
    private ListView listViewProduct;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        productList = new ArrayList<>();
        createProductList();
        productAdapter = new ProductAdapter(productList);
        listViewProduct = findViewById(R.id.product_lv);
        listViewProduct.setAdapter(productAdapter);


    }

    public void createProductList() {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setPrice(120.000);
        Product product1 = new Product();
        product1.setName("Samsung Galaxy A70");
        product1.setProductPrice(productPrice);
        Product product2 = new Product();
        product2.setName("Iphone 10 Max");
        product2.setProductPrice(productPrice);
        Product product3 = new Product();
        product3.setName("Xiaomi Redmi Note 8");
        product3.setProductPrice(productPrice);
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
    }
}