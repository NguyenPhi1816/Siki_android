package com.example.siki.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.model.Category;
import com.squareup.picasso.Picasso;

public class CategoryAddActivity extends AppCompatActivity {
    EditText edtTenLoaiSp, edtMoTaLoaiSp, edtAnhLoaiSp;
    Button btnBack, btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
        setControl();
        setEvent();

    }

    private void setEvent() {
        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
        categoryDatabase.open();

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAddActivity.this, CategoryListActivity.class);
                startActivity(intent);
            }
        });
        Dialog dialog = new Dialog(this);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.dialog_add);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                Button btnYes = dialog.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Category category = new Category();
                        if (edtTenLoaiSp.getText().toString().isEmpty()) {
                            edtTenLoaiSp.setError("Trường này là bắt buộc!");
                            edtTenLoaiSp.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            category.setName(edtTenLoaiSp.getText().toString());
                        }

                        if (edtMoTaLoaiSp.getText().toString().isEmpty()) {
                            edtMoTaLoaiSp.setError("Trường này là bắt buộc!");
                            edtMoTaLoaiSp.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            category.setDescription(edtMoTaLoaiSp.getText().toString());
                        }

                        if (edtAnhLoaiSp.getText().toString().isEmpty()) {
                            edtAnhLoaiSp.setError("Trường này là bắt buộc!");
                            edtAnhLoaiSp.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            category.setImagePath(edtAnhLoaiSp.getText().toString());
                        }

                        categoryDatabase.addCategory(category);
                        Intent intent = new Intent(CategoryAddActivity.this, CategoryListActivity.class);
                        startActivity(intent);
                        Toast.makeText(CategoryAddActivity.this, "Thêm loại sản phẩm thành công", Toast.LENGTH_LONG).show();
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
        edtTenLoaiSp = findViewById(R.id.tenLoaiSp);
        edtMoTaLoaiSp = findViewById(R.id.moTaLoaiSp);
        edtAnhLoaiSp = findViewById(R.id.anhLoaiSP);

        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);
    }
}