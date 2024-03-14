package com.example.siki.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siki.R;
import com.example.siki.database.ProductDatabase;
import com.example.siki.model.Product;
import com.example.siki.model.ProductPrice;

import java.util.ArrayList;
import java.util.List;

public class ProductAddActivity extends AppCompatActivity {
    EditText edtTenSp, edtMaSp, edtGiaSp, edtAnhSp;
    TextView tvLoaiSp;
    Spinner spLoaiSp;
    List<String> listLoaiSp = new ArrayList<>();
    Button btnBack, btnThem;

    private void khoiTao() {
        listLoaiSp.add("Điện thoại");
        listLoaiSp.add("Laptop");
        listLoaiSp.add("PC");
        listLoaiSp.add("Vật dụng cá nhân");
        listLoaiSp.add("Đồ nội thất");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        khoiTao();
        setControl();
        setEvent();

    }

    private void setEvent() {
        ProductDatabase productDatabase = new ProductDatabase(this);
        productDatabase.open();
        ArrayAdapter<String> loaiSpAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listLoaiSp);
        loaiSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiSp.setAdapter(loaiSpAdapter);

        List<String> selectedItems = new ArrayList<>();
        spLoaiSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirstSelection = true;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (isFirstSelection) {
                    isFirstSelection = false;
                    return;
                }
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem);
                } else {
                    selectedItems.add(selectedItem);
                }

                // Cập nhật TextView để hiển thị danh sách các mục đã chọn
                StringBuilder selectedItemsText = new StringBuilder("Loại sản phẩm đã chọn:\n");
                for (String item : selectedItems) {
                    selectedItemsText.append(item).append("\n");
                }
                tvLoaiSp.setText(selectedItemsText.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProductAddActivity.this, ProductListActivity.class);
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
                        Product product = new Product();
                        ProductPrice productPrice = new ProductPrice();
                        productPrice.setPrice(Double.parseDouble(edtGiaSp.getText().toString()));
                        product.setId(Long.parseLong(edtMaSp.getText().toString()));
                        product.setImagePath(edtAnhSp.getText().toString());
                        product.setName(edtTenSp.getText().toString());
                        product.setProductPrice(productPrice);
                        productDatabase.addProduct(product);
                        Intent intent = new Intent(ProductAddActivity.this, ProductListActivity.class);
                        startActivity(intent);
                        Toast.makeText(ProductAddActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
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
        edtTenSp = findViewById(R.id.tenSp);
        edtGiaSp = findViewById(R.id.giaSp);
        edtAnhSp = findViewById(R.id.anhSP);
        edtMaSp = findViewById(R.id.maSp);

        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);

        spLoaiSp = findViewById(R.id.spLoaiSp);

        tvLoaiSp = findViewById(R.id.tvLoaiSp);
    }
}