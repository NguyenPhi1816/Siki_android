package com.example.siki.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.siki.Adapter.StoreRecycleAdapter;
import com.example.siki.R;
import com.example.siki.database.CartDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Cart;
import com.example.siki.model.User;
import com.example.siki.variable.GlobalVariable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HomeActivity extends AppCompatActivity  {
    private List<Cart> cartList = new ArrayList<>();
    BottomNavigationView bottom_navigation;
    private UserDataSource userDataSource;
    private Map<String, List<Cart>> storeProductMap = new HashMap<>();

    private GlobalVariable globalVariable = new GlobalVariable();

    private ProductDatabase productDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setControl();
        setEvent() ;
    }

    private void setEvent() {
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    redirectHomeFragment();
                    return true;
                }

                if(item.getItemId() == R.id.nav_cart){
                    redirectCartFragment();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readCartDb();
    }

    public void readCartDb() {
        userDataSource = new UserDataSource(this);
        userDataSource.open();
        productDatabase = new ProductDatabase(this);
        productDatabase.open();

        // fake data
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
        }
    }


    private void redirectCartFragment() {
        Fragment fragment = new CartFragment(this, cartList, storeProductMap);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    private void redirectHomeFragment() {
        Fragment fragment  = new HomeFragment(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    private void setControl() {
        bottom_navigation= findViewById(R.id.bottom_navigation);
    }
}