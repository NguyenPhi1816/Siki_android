package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.database.ProductCategoryDatabase;
import com.example.siki.database.ProductDatabase;
import com.example.siki.model.Product;
import com.example.siki.model.ProductCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    String imagePath;
    Spinner spLoaiSp;
    EditText edtTenSp, edtMaSp, edtGiaSp, edtLoaiSp, edtSoLuongSp;
    TextView tvLoaiSp;
    Button btnBack, btnSua, btnAnhSp;
    ImageView ivAnhSp, imgAnhSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setControl();
        setEvent();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Xử lý kết quả trả về từ Android Image Picker
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Lấy URI của ảnh được chọn
            Uri selectedImageUri = data.getData();
            getContentResolver().takePersistableUriPermission(selectedImageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            imagePath = selectedImageUri.toString();
            imgAnhSp.setImageURI(selectedImageUri);

        }
    }

    private void setEvent() {
        ProductDatabase productDatabase = new ProductDatabase(this);
        productDatabase.open();
        ProductCategoryDatabase productCategoryDatabase = new ProductCategoryDatabase(this);
        productCategoryDatabase.open();
        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
        categoryDatabase.open();
        Map<Integer, String> listCategory = categoryDatabase.getAllCategory();
        List<String> listLoaiSp = new ArrayList<>(listCategory.values());
        listLoaiSp.add(0, "Chọn loại sản phẩm");
        ArrayAdapter<String> loaiSpAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listLoaiSp);
        loaiSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiSp.setAdapter(loaiSpAdapter);

        List<String> selectedItems = new ArrayList<>();
        spLoaiSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                for (Map.Entry<Integer, String> entry : listCategory.entrySet()) {
                    if (entry.getValue().equals(selectedItem)) {
                        Picasso.get().load(categoryDatabase.findImagePathById(Math.toIntExact(entry.getKey()))).into(ivAnhSp);
                    }
                }

                if (selectedItem.equals("Chọn loại sản phẩm")) {
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

        btnAnhSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); // Chỉ định loại hình ảnh
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }


        });

        Product product = (Product) getIntent().getSerializableExtra("product");
        if (product != null) {
            edtGiaSp.setText(product.getPrice()+"");
            edtMaSp.setText(product.getId()+"");
            edtTenSp.setText(product.getName());
            edtSoLuongSp.setText(product.getQuantity()+"");
            imgAnhSp.setImageURI(Uri.parse(product.getImagePath()));
            imagePath = product.getImagePath();
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
                        product.setId(Long.valueOf(edtMaSp.getText().toString()));
                        if (edtTenSp.getText().toString().isEmpty()) {
                            edtTenSp.setError("Trường này là bắt buộc!");
                            edtTenSp.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            product.setName(edtTenSp.getText().toString());
                        }
                        if (edtGiaSp.getText().toString().isEmpty()) {
                            edtGiaSp.setError("Trường này là bắt buộc!");
                            edtGiaSp.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            product.setPrice(Double.parseDouble(edtGiaSp.getText().toString()));
                        }
                        if (edtSoLuongSp.getText().toString().isEmpty()) {
                            edtSoLuongSp.setError("Trường này là bắt buộc!");
                            edtSoLuongSp.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            product.setQuantity(Integer.parseInt(edtSoLuongSp.getText().toString()));
                        }
                        product.setImagePath(imagePath);

                        productDatabase.updateProduct(product);

                        if (!selectedItems.isEmpty()) {
                            productCategoryDatabase.deleteByProductId(product.getId());
                            for (String item : selectedItems) {
                                ProductCategory productCategory = new ProductCategory();
                                productCategory.setProductId(Long.parseLong(edtMaSp.getText().toString()));
                                for (Map.Entry<Integer, String> entry : listCategory.entrySet()) {
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
        edtMaSp = findViewById(R.id.maSp);
        edtLoaiSp = findViewById(R.id.loaiSp);
        edtSoLuongSp = findViewById(R.id.soLuongSp);

        btnSua = findViewById(R.id.btnSua);
        btnBack = findViewById(R.id.btnBack);
        btnAnhSp = findViewById(R.id.btnAnhSP);

        tvLoaiSp = findViewById(R.id.tvLoaiSp);

        spLoaiSp = findViewById(R.id.spLoaiSp);

        ivAnhSp = findViewById(R.id.ivAnhSp);
        imgAnhSp = findViewById(R.id.imgAnhSp);
    }
}