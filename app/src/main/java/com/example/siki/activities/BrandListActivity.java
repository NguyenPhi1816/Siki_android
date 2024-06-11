package com.example.siki.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siki.API.BrandApiService;
import com.example.siki.API.CategoryApiService;
import com.example.siki.API.retrofit.RetrofitClient;
import com.example.siki.Adapter.BrandAdapter;
import com.example.siki.Adapter.CategoryAdapter;
import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.model.Brand;
import com.example.siki.model.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandListActivity extends AppCompatActivity {
    private List<Brand> brandList = new ArrayList<>();
    private ListView listViewBrand;
    private BrandAdapter brandAdapter;
    Button btnThem, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);
        setControl();
        setEvent();

    }

    private void setEvent() {
        brandAdapter = new BrandAdapter(this, brandList);
        listViewBrand = findViewById(R.id.brand_lv);
        listViewBrand.setAdapter(brandAdapter);
        btnThem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(BrandListActivity.this, BrandAddActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrandListActivity.this, MenuProduct_ProductCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callApi();
    }

//    private void readDb() {
//        brandList.clear();
//        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
//        categoryDatabase.open();
//        categoryList.addAll(categoryDatabase.readDb());
//        categoryAdapter.notifyDataSetChanged();
//    }

    private void callApi() {
        brandList.clear();
        BrandApiService brandApiService = RetrofitClient.getRetrofitInstance().create(BrandApiService.class);
        brandApiService.getAllBrands().enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                List<Brand> listBrandGet = response.body();
                if (listBrandGet != null) {
                    brandList.addAll(listBrandGet);
                    brandAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                System.out.println("Brand call api list failed!");
            }
        });
    }

    private void setControl() {
        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);
    }


}