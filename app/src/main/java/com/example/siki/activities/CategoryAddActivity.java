package com.example.siki.activities;

import static com.example.siki.activities.BrandAddActivity.callImagePathApi;
import static com.example.siki.activities.BrandAddActivity.getFileFromUri;

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
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.siki.API.CategoryApiService;
import com.example.siki.API.MediaApiResponseListener;
import com.example.siki.API.dto.CategoryDto;
import com.example.siki.API.retrofit.RetrofitClient;
import com.example.siki.R;
import com.example.siki.model.Category;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText edtTenLoaiSp, edtMoTaLoaiSp;
    Button btnBack, btnThem, btnAnhLoaiSp;
    ImageView imgAnhLoaiSp;

    Spinner spLoaiSpCha;

    Uri selectedImageUri;

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
            selectedImageUri = data.getData();
            getContentResolver().takePersistableUriPermission(selectedImageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            //imgAnhLoaiSp.setImageURI(selectedImageUri);
            Picasso.get().load(selectedImageUri).into(imgAnhLoaiSp);

        }
    }

    private void callGetListCategoryApi(Map<Integer, String> listCategoryIdName) {
        CategoryApiService categoryApiService = RetrofitClient.getRetrofitInstance().create(CategoryApiService.class);
        categoryApiService.getListCategory().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> listCategoryGet = response.body();
                if (listCategoryGet != null) {
                    for (Category c : listCategoryGet) {
                        listCategoryIdName.put(c.getId(), c.getName());
                    }
                    List<String> listLoaiSp = new ArrayList<>(listCategoryIdName.values()) ;
                    listLoaiSp.add(0, "Chọn loại sản phẩm");
                    ArrayAdapter<String> loaiSpAdapter = new ArrayAdapter<>(CategoryAddActivity.this,
                            android.R.layout.simple_spinner_item, listLoaiSp);
                    loaiSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spLoaiSpCha.setAdapter(loaiSpAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println("Category call api list failed!");
            }
        });
    }

    private void setEvent() {
        Map<Integer, String> listCategoryIdName = new HashMap<>();
        callGetListCategoryApi(listCategoryIdName);
        CategoryDto categoryDto = new CategoryDto();

        spLoaiSpCha.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                for (Map.Entry<Integer, String> entry : listCategoryIdName.entrySet()) {
                    if (entry.getValue().equals(selectedItem)) {
                        categoryDto.setCategoryParentId(entry.getKey());
                    }
                }

                if (selectedItem.equals("Chọn loại sản phẩm")) {
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

                        File file = getFileFromUri(CategoryAddActivity.this, selectedImageUri);

                        callImagePathApi(file, new MediaApiResponseListener() {
                            @Override
                            public void onUrlReceived(String imageUrl) {
                                categoryDto.setImage(imageUrl);
                                callApi(categoryDto);
                            }

                            @Override
                            public void onFailure(String errorMessage) {

                            }
                        });


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

        spLoaiSpCha = findViewById(R.id.spLoaiSpCha);
    }
}