package com.example.siki.API.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.siki.API.CategoryApiService;
import com.example.siki.R;
import com.example.siki.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApi();
            }
        });
    }

    private void callApi() {
        CategoryApiService categoryApiService = RetrofitClient.getRetrofitInstance().create(CategoryApiService.class);
        categoryApiService.getListCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoryList = response.body();
                if (categoryList != null) {
                    Toast.makeText(MainActivity.this, categoryList.get(1).getName(), Toast.LENGTH_LONG).show();
                }  else {
                    Toast.makeText(MainActivity.this, "null list category", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_LONG).show();
            }
        });

//        apiService.test().enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user = response.body();
//                if (user != null) {
//                    Toast.makeText(MainActivity.this, user.getEmail(), Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(MainActivity.this, "null user", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_LONG).show();
//            }
//        });

    }
}