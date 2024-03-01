package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.siki.R;
import com.example.siki.RecycleViewAdapter.CartAdapter;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.model.ProductPrice;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private List<Cart> cartList = new ArrayList<>() ;
    private RecyclerView cartItemRecycle;
    private CartAdapter cartAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartItemRecycle = findViewById(R.id.cart_recycle);
        createCartList();
        cartAdapter = new CartAdapter(this,cartList);
        cartItemRecycle.setAdapter(cartAdapter);
        cartItemRecycle.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createCartList() {
        // Todo: get data from api
        ProductPrice productPrice = new ProductPrice();
        productPrice.setPrice(120.000);
        Product product1 = new Product();
        product1.setName("Sam sung1");
        product1.setProductPrice(productPrice);
        Product product2 = new Product();
        product1.setName("Sam sung2");
        product1.setProductPrice(productPrice);
        Product product3 = new Product();
        product1.setName("Sam sung3");
        product1.setProductPrice(productPrice);

        Cart cart1 = new Cart();
        cart1.setQuantity(1);
        cart1.setProduct(product1);

        Cart cart2 = new Cart();
        cart1.setQuantity(1);
        cart1.setProduct(product2);

        Cart cart3 = new Cart();
        cart1.setQuantity(1);
        cart1.setProduct(product3);
        cartList.add(cart1);
        cartList.add(cart2);
        cartList.add(cart3);
    }
}