package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.siki.Adapter.StoreRecycleAdapter;
import com.example.siki.R;

import com.example.siki.database.CartDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.model.Store;
import com.example.siki.model.User;
import com.example.siki.variable.GlobalVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartActivity extends AppCompatActivity {
    private List<Cart> cartList = new ArrayList<>();
    private TextView tv_cart_totalPrice;
    private CheckBox cb_cart_total;
    private Button btn_delete_total_carts, btn_cart_order;
    private RecyclerView storeRecycle;

    private UserDataSource userDataSource   ;
    private CartDatasource cartDatasource;

    private ProductDatabase productDatabase;

    private GlobalVariable globalVariable = new GlobalVariable();
    private Map<String, List<Cart>> storeProductMap = new HashMap<>();
    private final String cartMessage = "Tất cả %d sản phẩm";

    private final String btnOrderMessage = "Mua hàng (%d)";

    private StoreRecycleAdapter storeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setControl();
        setEvent();
    }

    private void setEvent() {
        cartDatasource = new CartDatasource(this);
        cartDatasource.open();

        //Todo: get all cart that is selected -> get total price
        tv_cart_totalPrice.setText(getTotalOfCartIsSelected()+"");
        List<Cart> selectingCarts = cartList.stream().map(cart -> {
            if (cart.isChosen()) {
                return cart;
            }
            return null;
        }).collect(Collectors.toList());
        cb_cart_total.setText(String.format(cartMessage, selectingCarts.size()));
        btn_cart_order.setText(String.format(btnOrderMessage, selectingCarts.size()));
        storeAdapter = new StoreRecycleAdapter(storeProductMap, this);
        storeRecycle.setAdapter(storeAdapter);
        storeRecycle.setLayoutManager(new GridLayoutManager(this, 1));
        cb_cart_total.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cb_cart_total.setChecked(isChecked);
                if (globalVariable.getAuthUser() != null) {
                    cartDatasource.updateSelectedCartByUser(globalVariable.getAuthUser().getId() , isChecked);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        readDb();
    }

    private void readDb() {
        // Todo: fake data user when login successful
        userDataSource = new UserDataSource(this);
        userDataSource.open();
        productDatabase = new ProductDatabase(this);
        productDatabase.open();
        User user = userDataSource.getUserById(1);
        globalVariable.setAuthUser(user);

        if (globalVariable.getAuthUser() != null) {
            // Get cart by user who login successful
           User currentUser = globalVariable.getAuthUser();
           cartList.clear();
           CartDatasource cartDatasource = new CartDatasource(this);
           cartDatasource.open();
           cartList.addAll(cartDatasource.findByUser(currentUser.getId(), productDatabase, userDataSource));
            storeProductMap = cartList.stream()
                    .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getStore().getName()));

           // Set value for component when data
            tv_cart_totalPrice.setText(getTotalOfCartIsSelected()+"");
            int selectingCart = (int) cartList.stream().filter(Cart::isChosen).count();
            cb_cart_total.setText(String.format(cartMessage, selectingCart));
            btn_cart_order.setText(String.format(btnOrderMessage, selectingCart));
            storeAdapter = new StoreRecycleAdapter(storeProductMap, this);
            storeRecycle.setAdapter(storeAdapter);
           storeAdapter.notifyDataSetChanged();
       }
    }

    private void setControl () {
        cb_cart_total = findViewById(R.id.cb_cart_total);
        storeRecycle = findViewById(R.id.cart_recycleView);
        tv_cart_totalPrice = findViewById(R.id.tv_cart_totalPrice);
        btn_delete_total_carts = findViewById(R.id.btn_delete_total_carts);
        btn_cart_order = findViewById(R.id.btn_cart_order);
    }


    private double getTotalOfCartIsSelected() {
        double totalPrice = 0 ;
        if (cartList.size() > 0) {
            for (Cart cart: cartList) {
                if (cart.getProduct() != null && cart.isChosen()) {
                    totalPrice+=cart.getProduct().getPrice()  ;
                }
            }
        }
        return totalPrice;
    }
}