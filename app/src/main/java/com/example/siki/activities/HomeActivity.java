package com.example.siki.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.siki.API.CartApi;
import com.example.siki.R;
import com.example.siki.activities.fragment.CartFragment;
import com.example.siki.activities.fragment.HomeFragment;
import com.example.siki.activities.fragment.ProfileFragment;
import com.example.siki.database.CartDatasource;
import com.example.siki.database.ProductDatabase;
import com.example.siki.database.UserDataSource;
import com.example.siki.dto.cart.CartDto;
import com.example.siki.model.Cart;
import com.example.siki.model.User;
import com.example.siki.variable.GlobalVariable;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity  {
    private List<Cart> cartList = new ArrayList<>();
    BottomNavigationView bottom_navigation;
    private UserDataSource userDataSource;
    private Map<String, List<Cart>> storeProductMap = new HashMap<>();
    private ProductDatabase productDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment(this))
                .commit();
        setControl();
        setEvent() ;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int fragment = extras.getInt("fragment");
            if (fragment == R.id.nav_cart) {
                redirectCartFragment();
                bottom_navigation.setSelectedItemId(R.id.nav_cart);
            }else if (fragment == R.id.nav_profile){
                redirectProfileFragment();
                bottom_navigation.setSelectedItemId(R.id.nav_profile);
            }
        }
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

                if(item.getItemId() == R.id.nav_profile) {
                    redirectProfileFragment();
                    return true;
                }
                return false;
            }
        });
    }

    private void redirectProfileFragment() {
        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        boolean isLoggedIn = globalVariable.isLoggedIn();
        if (isLoggedIn){
            Fragment fragment  = new ProfileFragment(this, globalVariable);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        } else {
            Intent activityChangeIntent = new Intent(this, LoginActivity.class);
            this.startActivity(activityChangeIntent);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        /*readCartDb();*/
    }

    public void readCartDb() {
        userDataSource = new UserDataSource(this);
        userDataSource.open();
        productDatabase = new ProductDatabase(this);
        productDatabase.open();

        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        if (globalVariable.isLoggedIn()) {
            if (globalVariable.getAuthUser() != null) {
                User currentUser = globalVariable.getAuthUser();
                cartList.clear();
                CartDatasource cartDatasource = new CartDatasource(this);
                cartDatasource.open();

                CartApi.cartApi.getCartByUserId(currentUser.getId(), globalVariable.getAccess_token()).enqueue(new Callback<List<CartDto>>() {
                    @Override
                    public void onResponse(Call<List<CartDto>> call, Response<List<CartDto>> response) {
                        List<CartDto> cartDtos = response.body();
                        List<Cart> carts = cartDtos.stream().map(cartDto -> new Cart(cartDto)).collect(Collectors.toList());
                        cartList.addAll(carts);
                        storeProductMap = cartList.stream()
                                .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getStore().getName()));
                    }

                    @Override
                    public void onFailure(Call<List<CartDto>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT);
                    }
                });

            }else {
                cartList = new ArrayList<>();
                storeProductMap = new HashMap<>();
            }
        }

    }


    private void redirectCartFragment() {
        UserDataSource userDataSource1 = new UserDataSource(this);
        userDataSource1.open();
        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        if (globalVariable.isLoggedIn()) {
            if (globalVariable.getAuthUser() == null) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            } else {
                Fragment fragment = new CartFragment(this, cartList, storeProductMap, globalVariable);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            }
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void redirectHomeFragment() {
        Fragment fragment  = new HomeFragment(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }

    private void setControl() {
        bottom_navigation= findViewById(R.id.bottom_navigation);
    }
}