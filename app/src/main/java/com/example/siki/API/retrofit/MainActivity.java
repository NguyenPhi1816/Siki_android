package com.example.siki.API.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.siki.API.ApiService;
import com.example.siki.R;
import com.example.siki.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<User> call = apiService.getUser();  // Thay 1 bằng ID người dùng mà bạn muốn lấy

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        System.out.println("User ID: " + user.getId());
                        System.out.println("User Name: " + user.getFullname());
                        System.out.println("User Email: " + user.getEmail());
                    } else {
                        System.out.println("Response body is null");
                    }
                } else {
                    Log.e("MainActivity", "Request Failed with code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("MainActivity", "Network Request Failed", t);
            }
        });
    }
}