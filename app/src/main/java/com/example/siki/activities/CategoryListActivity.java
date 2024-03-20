package com.example.siki.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siki.Adapter.CategoryAdapter;
import com.example.siki.Adapter.ProductAdapter;
import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.database.ProductDatabase;
import com.example.siki.model.Category;
import com.example.siki.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {
    private List<Category> categoryList = new ArrayList<>();
    private ListView listViewCategory;
    private CategoryAdapter categoryAdapter;
    Button btnThem, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        setControl();
        setEvent();

    }

    private void setEvent() {
        categoryAdapter = new CategoryAdapter(this, categoryList);
        listViewCategory = findViewById(R.id.category_lv);
        listViewCategory.setAdapter(categoryAdapter);
        btnThem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CategoryListActivity.this, CategoryAddActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryListActivity.this, MenuProduct_ProductCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readDb();
    }

    private void readDb() {
        categoryList.clear();
        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
        categoryDatabase.open();
        categoryList.addAll(categoryDatabase.readDb());
        categoryAdapter.notifyDataSetChanged();
    }

    private void setControl() {
        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);
    }


}