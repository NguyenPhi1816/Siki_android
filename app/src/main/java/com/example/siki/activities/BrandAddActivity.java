package com.example.siki.activities;

import android.app.Dialog;
import android.content.Context;
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
import com.example.siki.API.MediaApiResponseListener;
import com.example.siki.API.MediaApiService;
import com.example.siki.API.dto.BrandDto;
import com.example.siki.API.dto.CategoryDto;
import com.example.siki.API.dto.MediaDto;
import com.example.siki.API.retrofit.RetrofitClient;
import com.example.siki.R;
import com.example.siki.variable.GlobalVariable;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandAddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText edtTenTH;
    Button btnBack, btnThem, btnLogoTH;
    ImageView imgLogoTH;


    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_add);
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

            //imgLogoTH.setImageURI(selectedImageUri);
            Picasso.get().load(selectedImageUri).into(imgLogoTH);


        }
    }



    private void setEvent() {

        btnLogoTH.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(BrandAddActivity.this, BrandListActivity.class);
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
                        BrandDto brandDto = new BrandDto();
                        if (edtTenTH.getText().toString().isEmpty()) {
                            edtTenTH.setError("Trường này là bắt buộc!");
                            edtTenTH.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            brandDto.setName(edtTenTH.getText().toString());
                        }

                        File file = getFileFromUri(BrandAddActivity.this, selectedImageUri);

                        callImagePathApi(file, new MediaApiResponseListener() {
                            @Override
                            public void onUrlReceived(String imageUrl) {
                                brandDto.setLogo(imageUrl);
                                callApi(brandDto);
                            }

                            @Override
                            public void onFailure(String errorMessage) {

                            }
                        });


                        Intent intent = new Intent(BrandAddActivity.this, BrandListActivity.class);
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

    private void callApi(BrandDto brandDto) {
        GlobalVariable globalVariable = (GlobalVariable) getApplication();
        BrandApiService brandApiService = RetrofitClient.getRetrofitInstance().create(BrandApiService.class);
        brandApiService.saveBrand(brandDto, globalVariable.getAccess_token()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(BrandAddActivity.this, "Thêm thương hiệu thành công", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println("Brand call api add failed!");
            }
        });
    }

    public static void callImagePathApi(File file, MediaApiResponseListener listener) {
        RequestBody type = RequestBody.create(MediaType.parse("multipart/form-data"), "IMAGE");
        RequestBody image = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part requestImage = MultipartBody.Part.createFormData("file", file.getName(), image);
        MediaApiService.apiService.createMedia(requestImage, type).enqueue(new Callback<MediaDto>() {
            @Override
            public void onResponse(Call<MediaDto> call, Response<MediaDto> response) {
                MediaDto mediaDto = response.body();
                if (mediaDto != null) {
                    listener.onUrlReceived(mediaDto.getUrl());
                } else {
                    listener.onFailure("Url image is null");
                }
            }
            @Override
            public void onFailure(Call<MediaDto> call, Throwable t) {
                listener.onFailure("Brand call api get image path failed!");
            }
        });
    }

    // Hàm này để tạo file từ URI
    public static File getFileFromUri(Context context, Uri uri) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // Mở InputStream từ URI
            inputStream = context.getContentResolver().openInputStream(uri);
            // Tạo tên file từ URI (bạn có thể thay đổi cách này nếu cần)
            String fileName = "file_from_uri";
            // Tạo đối tượng File trong thư mục cache của ứng dụng
            File file = new File(context.getCacheDir(), fileName);
            // Tạo OutputStream để ghi dữ liệu vào file
            outputStream = new FileOutputStream(file);
            // Đọc dữ liệu từ InputStream và ghi vào OutputStream
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            // Trả về đối tượng File đã tạo
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ nếu có
            return null;
        } finally {
            // Đóng InputStream và OutputStream khi không cần dùng nữa
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void setControl() {
        edtTenTH = findViewById(R.id.tenTH);

        btnLogoTH = findViewById(R.id.btnLogoTH);
        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);

        imgLogoTH = findViewById(R.id.imgLogoTH);
    }
}