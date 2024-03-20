package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.database.ProductCategoryDatabase;
import com.example.siki.database.ProductDatabase;
import com.example.siki.model.Product;
import com.example.siki.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {
    Spinner spLoaiSp;
    EditText edtTenSp, edtMaSp, edtGiaSp, edtAnhSp, edtLoaiSp;
    TextView tvLoaiSp;
    Button btnBack, btnSua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setControl();
        setEvent();

    }

    private void setEvent() {
        ProductDatabase productDatabase = new ProductDatabase(this);
        productDatabase.open();
        ProductCategoryDatabase productCategoryDatabase = new ProductCategoryDatabase(this);
        productCategoryDatabase.open();
        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
        categoryDatabase.open();
        Map<Long, String> listCategory = categoryDatabase.getAllCategory();
        List<String> listLoaiSp = new ArrayList<>(listCategory.values()) ;
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
        Product product = (Product) getIntent().getSerializableExtra("product");
        if (product != null) {
            edtGiaSp.setText(product.getPrice().toString());
            edtMaSp.setText(product.getId().toString());
            edtTenSp.setText(product.getName());
            edtAnhSp.setText(product.getImagePath());
            List<String> list = productCategoryDatabase.findNameCategoryByProductId(product.getId());
            StringBuilder rs = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                rs.append(list.get(i));
                if (i < list.size() - 1) {
                    rs.append(", ");
                }
            }
            edtLoaiSp.setText(rs.toString());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, ProductListActivity.class);
                ProductDetailActivity.this.startActivity(intent);
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
                        Product product = new Product();
                        product.setPrice(Double.parseDouble(edtGiaSp.getText().toString()));
                        product.setId(Long.parseLong(edtMaSp.getText().toString()));
                        product.setImagePath(edtAnhSp.getText().toString());
                        product.setName(edtTenSp.getText().toString());
                        productDatabase.updateProduct(product);

                        if (!selectedItems.isEmpty()) {
                            productCategoryDatabase.deleteByProductId(product.getId());
                            for (String item : selectedItems) {
                                ProductCategory productCategory = new ProductCategory();
                                productCategory.setProductId(Long.parseLong(edtMaSp.getText().toString()));
                                for (Map.Entry<Long, String> entry : listCategory.entrySet()) {
                                    if (entry.getValue().equals(item)) {
                                        productCategory.setCategoryId(entry.getKey());
                                    }
                                }
                                productCategoryDatabase.addProductCategory(productCategory);
                            }
                        }

                        Intent intent = new Intent(ProductDetailActivity.this, ProductListActivity.class);
                        startActivity(intent);
                        Toast.makeText(ProductDetailActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_LONG).show();
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
        edtLoaiSp = findViewById(R.id.loaiSp);

        btnSua = findViewById(R.id.btnSua);
        btnBack = findViewById(R.id.btnBack);
        tvLoaiSp = findViewById(R.id.tvLoaiSp);

        spLoaiSp = findViewById(R.id.spLoaiSp);
    }
}