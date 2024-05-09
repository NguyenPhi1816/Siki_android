package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.siki.Adapter.CategoryAdapter;
import com.example.siki.Adapter.PromotionAdapter;
import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.database.PromotionDataSource;
import com.example.siki.model.Category;
import com.example.siki.model.Product;
import com.example.siki.model.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionListActivity extends AppCompatActivity {
    private List<Promotion> promotionList = new ArrayList<>();
    private ListView lvPromotion;
    private PromotionAdapter promotionAdapter;
    Button btnThem, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_list);
        setControl();
        setEvent();
    }

    private void setEvent() {
        promotionAdapter = new PromotionAdapter(this, promotionList);
        lvPromotion = findViewById(R.id.promotion_lv);
        lvPromotion.setAdapter(promotionAdapter);
        btnThem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PromotionListActivity.this, PromotionAddActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PromotionListActivity.this, MenuProduct_ProductCategoryActivity.class);
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
        promotionList.clear();
        PromotionDataSource promotionDataSource = new PromotionDataSource(this);
        promotionDataSource.open();
        promotionList.addAll(promotionDataSource.readDb());
        promotionAdapter.notifyDataSetChanged();
    }

    private void setControl() {
        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);
    }
}