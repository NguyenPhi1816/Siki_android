package com.example.siki.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siki.API.CategoryApiService;
import com.example.siki.API.dto.CategoryDto;
import com.example.siki.API.retrofit.RetrofitClient;
import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.model.Category;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    String imagePath;
    EditText edtMaLoaiSp, edtTenLoaiSp, edtMoTaLoaiSp;
    Button btnBack, btnSua, btnAnhLoaiSp;
    ImageView imgAnhLoaiSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
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
            imgAnhLoaiSp.setImageURI(selectedImageUri);

        }
    }

    private void setEvent() {
        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
        categoryDatabase.open();
        Category category = (Category) getIntent().getSerializableExtra("category");
        if (category != null) {
            edtMaLoaiSp.setText(category.getId().toString());
            edtTenLoaiSp.setText(category.getName());
            edtMoTaLoaiSp.setText(category.getDescription());
            imgAnhLoaiSp.setImageURI(Uri.parse(category.getImage()));
            imagePath = category.getImage();

        }

        btnAnhLoaiSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); // Chỉ định loại hình ảnh
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }

            // Tạo Intent để mở hộp thoại chọn ảnh từ bộ nhớ thiết bị
        });

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
                        CategoryDto categoryDto = new CategoryDto();
                        Long id = (Long.valueOf(edtMaLoaiSp.getText().toString()));
                        Category category = new Category();
                        category.setId(Integer.valueOf(edtMaLoaiSp.getText().toString()));

                        if (edtTenLoaiSp.getText().toString().isEmpty()) {
                            edtTenLoaiSp.setError("Trường này là bắt buộc!");
                            edtTenLoaiSp.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            categoryDto.setName(edtTenLoaiSp.getText().toString());
                        }

                        if (edtMoTaLoaiSp.getText().toString().isEmpty()) {
                            edtMoTaLoaiSp.setError("Trường này là bắt buộc!");
                            edtMoTaLoaiSp.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            categoryDto.setDescription(edtMoTaLoaiSp.getText().toString());
                        }

                        categoryDto.setImage(imagePath);

                        callApi(Math.toIntExact(id), categoryDto);

                        Intent intent = new Intent(CategoryDetailActivity.this, CategoryListActivity.class);
                        startActivity(intent);
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

    private void callApi(Integer id, CategoryDto categoryDto) {
        CategoryApiService categoryApiService = RetrofitClient.getRetrofitInstance().create(CategoryApiService.class);
        categoryApiService.updateCategory(id, categoryDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(CategoryDetailActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Category call api update failed!");
            }
        });
    }

    private void setControl() {
        edtMaLoaiSp = findViewById(R.id.maLoaiSp);
        edtTenLoaiSp = findViewById(R.id.tenLoaiSp);
        edtMoTaLoaiSp = findViewById(R.id.moTaLoaiSp);

        btnAnhLoaiSp = findViewById(R.id.btnAnhLoaiSp);
        btnSua = findViewById(R.id.btnSua);
        btnBack = findViewById(R.id.btnBack);

        imgAnhLoaiSp = findViewById(R.id.imgAnhLoaiSp);
    }
}