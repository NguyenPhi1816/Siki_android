package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.siki.Adapter.StoreRecycleAdapter;
import com.example.siki.R;

import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.model.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartActivity extends AppCompatActivity {
    private List<Cart> cartList ;
    private TextView tv_cart_totalPrice;
    private CheckBox cb_cart_total;
    private RecyclerView storeRecycle;

    private String cartMessage = "Tất cả %d sản phẩm";

    private StoreRecycleAdapter storeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartList = new ArrayList<>();
        setControl();
        createCartList();
        Map<Store, List<Product>> storeProductMap = cartList.stream()
                .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getStore(),
                        Collectors.mapping(Cart::getProduct, Collectors.toList())));
        tv_cart_totalPrice.setText(getTotal()+"");
        cb_cart_total.setText(String.format(cartMessage, cartList.size()));
        storeAdapter = new StoreRecycleAdapter(storeProductMap);
        storeRecycle.setAdapter(storeAdapter);
        storeRecycle.setLayoutManager(new GridLayoutManager(this, 1));
    }

    private double getTotal() {
        double totalPrice = 0 ;
        if (cartList.size() > 0) {
            for (Cart cart: cartList) {
                if (cart.getProduct() != null) {
                    totalPrice+=cart.getProduct().getPrice()  ;
                }
            }
        }
        return totalPrice;
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

        Cart cart5 = new Cart();
        cart5.setId(5L);
        cart5.setQuantity(1);
        cart5.setProduct(product3);

        Cart cart6 = new Cart();
        cart6.setId(6L);
        cart6.setQuantity(1);
        cart6.setProduct(product4);

        cartList.add(cart1);
        cartList.add(cart2);
        cartList.add(cart3);
        cartList.add(cart4);
        cartList.add(cart5);
        cartList.add(cart6);
    }

    private void setControl () {
        cb_cart_total = findViewById(R.id.cb_cart_total);
        storeRecycle = findViewById(R.id.cart_recycleView);
        tv_cart_totalPrice = findViewById(R.id.tv_cart_totalPrice);
    }
}