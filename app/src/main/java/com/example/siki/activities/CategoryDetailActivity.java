package com.example.siki.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.model.Category;

public class CategoryDetailActivity extends AppCompatActivity {

    EditText edtMaLoaiSp, edtTenLoaiSp, edtMoTaLoaiSp;
    Button btnBack, btnSua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        setControl();
        setEvent();

    }

    private void setEvent() {
        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
        categoryDatabase.open();
        Category category = (Category) getIntent().getSerializableExtra("category");
        if (category != null) {
            edtMaLoaiSp.setText(category.getId().toString());
            edtTenLoaiSp.setText(category.getName());
            edtMoTaLoaiSp.setText(category.getDescription());

        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CategoryDetailActivity.this, CategoryListActivity.class);
                CategoryDetailActivity.this.startActivity(intent);
            }
        });
        Dialog dialog = new Dialog(this);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.dialog_update);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                Button btnYes = dialog.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Category category = new Category();
                        category.setId(Long.parseLong(edtMaLoaiSp.getText().toString()));
                        category.setName(edtTenLoaiSp.getText().toString());
                        category.setDescription(edtMoTaLoaiSp.getText().toString());
                        categoryDatabase.updateProduct(category);
                        Intent intent = new Intent(CategoryDetailActivity.this, CategoryListActivity.class);
                        startActivity(intent);
                        Toast.makeText(CategoryDetailActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_LONG).show();
                    }
                });

                Button btnNo = dialog.findViewById(R.id.btnNo);
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    private void setControl() {
        edtMaLoaiSp = findViewById(R.id.maLoaiSp);
        edtTenLoaiSp = findViewById(R.id.tenLoaiSp);
        edtMoTaLoaiSp = findViewById(R.id.moTaLoaiSp);

        btnSua = findViewById(R.id.btnSua);
        btnBack = findViewById(R.id.btnBack);
    }
}