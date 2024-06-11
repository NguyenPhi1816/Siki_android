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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siki.API.BrandApiService;
import com.example.siki.API.CategoryApiService;
import com.example.siki.API.MediaApiResponseListener;
import com.example.siki.API.dto.CategoryDto;
import com.example.siki.API.retrofit.RetrofitClient;
import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.model.Brand;
import com.example.siki.model.Category;
import com.squareup.picasso.Picasso;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandDetailActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    String imagePath;
    EditText edtMaTH, edtTenTH;
    Button btnBack, btnSua, btnLogoTh;
    ImageView imgLogoTh;

    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);
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
            imagePath = selectedImageUri.toString();
            //imgLogoTh.setImageURI(selectedImageUri);
            Picasso.get().load(selectedImageUri).into(imgLogoTh);


        }
    }

    private void setEvent() {
        Brand brand = (Brand) getIntent().getSerializableExtra("brand");
        if (brand != null) {
            edtMaTH.setText(brand.getId().toString());
            edtTenTH.setText(brand.getName());
            //imgLogoTh.setImageURI(Uri.parse(brand.getLogo()));
            Picasso.get().load(brand.getLogo()).into(imgLogoTh);
            imagePath = brand.getLogo();

        }

        btnLogoTh.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(BrandDetailActivity.this, BrandListActivity.class);
                BrandDetailActivity.this.startActivity(intent);
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
                        Brand brand1 = new Brand();
                        Long id = (Long.valueOf(edtMaTH.getText().toString()));

                        if (edtTenTH.getText().toString().isEmpty()) {
                            edtTenTH.setError("Trường này là bắt buộc!");
                            edtTenTH.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            brand1.setName(edtTenTH.getText().toString());
                        }

                        File file = getFileFromUri(BrandDetailActivity.this, selectedImageUri);

                        callImagePathApi(file, new MediaApiResponseListener() {
                            @Override
                            public void onUrlReceived(String imageUrl) {
                                brand1.setLogo(imageUrl);
                                callApi(Math.toIntExact(id), brand1);
                            }

                            @Override
                            public void onFailure(String errorMessage) {

                            }
                        });

                        Intent intent = new Intent(BrandDetailActivity.this, BrandListActivity.class);
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

    private void callApi(Integer id, Brand brand) {
        BrandApiService brandApiService = RetrofitClient.getRetrofitInstance().create(BrandApiService.class);
        brandApiService.updateBrand(id, brand).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(BrandDetailActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Brand call api update failed!");
            }
        });
    }

    private void setControl() {
        edtMaTH = findViewById(R.id.maTH);
        edtTenTH = findViewById(R.id.tenTH);

        btnLogoTh = findViewById(R.id.btnLogoTH);
        btnSua = findViewById(R.id.btnSua);
        btnBack = findViewById(R.id.btnBack);

        imgLogoTh = findViewById(R.id.imgLogoTH);
    }
}