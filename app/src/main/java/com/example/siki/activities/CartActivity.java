package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.siki.R;
import com.example.siki.Adapter.CartAdapter;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.model.ProductPrice;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private List<Cart> cartList ;
    private ListView cartListView;
    private CartAdapter cartAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartList = new ArrayList<>();
        cartListView = findViewById(R.id.cart_listview);
        createCartList();
        cartAdapter = new CartAdapter(cartList);
        cartListView.setAdapter(cartAdapter);
    }

    private void createCartList() {
        // Todo: get data from api
        ProductPrice productPrice = new ProductPrice();
        productPrice.setPrice(120.000);
        Product product1 = new Product();
        product1.setName("Sam sung1");
        product1.setProductPrice(productPrice);
        Product product2 = new Product();
        product2.setName("Sam sung2");
        product2.setProductPrice(productPrice);
        Product product3 = new Product();
        product3.setName("Sam sung3");
        product3.setProductPrice(productPrice);

        Cart cart1 = new Cart();
        cart1.setId(1L);
        cart1.setQuantity(1);
        cart1.setProduct(product1);

        Cart cart2 = new Cart();
        cart2.setId(2L);

        cart2.setQuantity(1);
        cart2.setProduct(product2);

        Cart cart3 = new Cart();
        cart3.setId(2L);
        cart3.setQuantity(1);
        cart3.setProduct(product3);


        cartList.add(cart1);
        cartList.add(cart2);
        cartList.add(cart3);
    }
}