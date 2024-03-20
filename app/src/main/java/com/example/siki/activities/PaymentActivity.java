package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.siki.Adapter.PaymentRecycleAdapter;
import com.example.siki.R;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.model.Store;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {
    private List<Cart> cartList ;
    private RecyclerView paymentRecycle;
    private PaymentRecycleAdapter paymentRecycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        cartList = new ArrayList<>();
        createCartList();
        paymentRecycleAdapter = new PaymentRecycleAdapter(cartList);
        paymentRecycle.setAdapter(paymentRecycleAdapter);
        /*paymentRecycle.setLayoutManager(new GridLayoutManager(this, 1));*/
    }
    private void createCartList() {
        // Todo: get data from api
        Store store1 = new Store();
        store1.setName("The gioi di dong");

        Store store2 = new Store();
        store2.setName("Apple");


        Product product1 = new Product();
        product1.setName("Sam sung1");
        product1.setPrice(120.0);


        Product product2 = new Product();
        product2.setName("Sam sung2");
        product2.setPrice(120.0);


        product1.setStore(store1);
        product2.setStore(store1);

        Product product3 = new Product();
        product3.setName("Sam sung3");
        product3.setPrice(120.0);

        Product product4 = new Product();
        product4.setName("Apple 1");
        product4.setPrice(120.0);

        product3.setStore(store2);
        product4.setStore(store2);

        Cart cart1 = new Cart();
        cart1.setId(1L);
        cart1.setQuantity(1);
        cart1.setProduct(product1);

        Cart cart2 = new Cart();
        cart2.setId(2L);
        cart2.setQuantity(1);
        cart2.setProduct(product2);

        Cart cart3 = new Cart();
        cart3.setId(3L);
        cart3.setQuantity(1);
        cart3.setProduct(product3);

        Cart cart4 = new Cart();
        cart4.setId(4L);
        cart4.setQuantity(1);
        cart4.setProduct(product4);


        cartList.add(cart1);
        cartList.add(cart2);
        cartList.add(cart3);
        cartList.add(cart4);
    }

}