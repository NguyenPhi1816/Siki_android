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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText edtTenLoaiSp, edtMoTaLoaiSp;
    Button btnBack, btnThem, btnAnhLoaiSp;
    ImageView imgAnhLoaiSp;

    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_add);
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

        btnAnhLoaiSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); // Chỉ định loại hình ảnh
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }


        });

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
                        CategoryDto categoryDto = new CategoryDto();
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


                        callApi(categoryDto);

                        Intent intent = new Intent(CategoryAddActivity.this, CategoryListActivity.class);
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

    private void callApi(CategoryDto categoryDto) {
        CategoryApiService categoryApiService = RetrofitClient.getRetrofitInstance().create(CategoryApiService.class);
        categoryApiService.saveCategory(categoryDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(CategoryAddActivity.this, "Thêm loại sản phẩm thành công", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Category call api add failed!");
            }
        });
    }

    private void setControl() {
        edtTenLoaiSp = findViewById(R.id.tenLoaiSp);
        edtMoTaLoaiSp = findViewById(R.id.moTaLoaiSp);

        btnAnhLoaiSp = findViewById(R.id.btnAnhLoaiSP);
        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);

        imgAnhLoaiSp = findViewById(R.id.imgAnhLoaiSp);
    }
}