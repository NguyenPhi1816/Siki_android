package com.example.siki.activities;

import static com.example.siki.activities.SignUpActivity.isValidDate;
import static com.example.siki.database.PromotionDataSource.convertStringToDate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.siki.FormValidator.FormValidator;
import com.example.siki.R;
import com.example.siki.database.CategoryDatabase;
import com.example.siki.database.ProductCategoryDatabase;
import com.example.siki.database.PromotionDataSource;
import com.example.siki.model.Category;
import com.example.siki.model.Promotion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PromotionAddActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    EditText edtTenKM, edtLyDoKM, edtNgayBDKM, edtNgayKTKM, edtPhanTramKM;
    Button btnBack, btnThem, btnAnhKM, btnNgayBDKM, btnNgayKTKM;
    ImageView imgAnhKM, ivAnhLoaiSp;
    Spinner spLoaiSp;
    Integer idCategory;
    String imagePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_add);
        setControl();
        setEvent();
    }

    private void setNgayBDKM() {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(PromotionAddActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        String formattedDay = String.format("%02d", dayOfMonth); // Định dạng ngày để có hai chữ số
                        String formattedMonth = String.format("%02d", (monthOfYear + 1)); // Định dạng tháng để có hai chữ số
                        edtNgayBDKM.setError(null);
                        edtNgayBDKM.setText(formattedDay + "-" + formattedMonth + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    private void setNgayKTKM() {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(PromotionAddActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        String formattedDay = String.format("%02d", dayOfMonth); // Định dạng ngày để có hai chữ số
                        String formattedMonth = String.format("%02d", (monthOfYear + 1)); // Định dạng tháng để có hai chữ số
                        edtNgayKTKM.setError(null);
                        edtNgayKTKM.setText(formattedDay + "-" + formattedMonth + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
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
            imgAnhKM.setImageURI(selectedImageUri);

        }
    }

    private void setEvent() {
        PromotionDataSource promotionDataSource = new PromotionDataSource(this);
        promotionDataSource.open();

        CategoryDatabase categoryDatabase = new CategoryDatabase(this);
        categoryDatabase.open();
        ProductCategoryDatabase productCategoryDatabase = new ProductCategoryDatabase(this);
        productCategoryDatabase.open();
        Map<Integer, String> listCategory = categoryDatabase.getAllCategory();
        List<String> listLoaiSp = new ArrayList<>(listCategory.values()) ;
        listLoaiSp.add(0, "Chọn loại sản phẩm");
        ArrayAdapter<String> loaiSpAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listLoaiSp);
        loaiSpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiSp.setAdapter(loaiSpAdapter);

        spLoaiSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                for (Map.Entry<Integer, String> entry : listCategory.entrySet()) {
                    if (entry.getValue().equals(selectedItem)) {
                        idCategory = entry.getKey();
                        Picasso.get().load(categoryDatabase.findImagePathById(Math.toIntExact(entry.getKey()))).into(ivAnhLoaiSp);
                    }
                }

                if (selectedItem.equals("Chọn loại sản phẩm")) {
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnAnhKM.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(PromotionAddActivity.this, PromotionListActivity.class);
                startActivity(intent);
            }
        });

        btnNgayBDKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNgayBDKM();
            }
        });

        btnNgayKTKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNgayKTKM();
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
                        Promotion promotion = new Promotion();
                        if (edtTenKM.getText().toString().isEmpty()) {
                            edtTenKM.setError("Trường này là bắt buộc!");
                            edtTenKM.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            promotion.setName(edtTenKM.getText().toString());
                        }

                        if (edtLyDoKM.getText().toString().isEmpty()) {
                            edtLyDoKM.setError("Trường này là bắt buộc!");
                            edtLyDoKM.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            promotion.setReason(edtLyDoKM.getText().toString());
                        }

                        if (edtPhanTramKM.getText().toString().isEmpty()) {
                            edtPhanTramKM.setError("Trường này là bắt buộc!");
                            edtPhanTramKM.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            promotion.setPercentPromotion(Integer.parseInt(edtPhanTramKM.getText().toString()));
                        }

                        Date currentDate = setTimeToMidNight(new Date());
                        Date startDate = convertStringToDate(edtNgayBDKM.getText().toString());
                        Date endDate = convertStringToDate(edtNgayKTKM.getText().toString());

                        if (edtNgayBDKM.getText().toString().isEmpty()) {
                            edtNgayBDKM.setError("Trường này là bắt buộc!");
                            edtNgayBDKM.requestFocus();
                            dialog.dismiss();
                            return;
                        } else if (!isValidDate(edtNgayBDKM.getText().toString())) {
                            edtNgayBDKM.setError("Định dạng không hợp lệ!");
                            edtNgayBDKM.requestFocus();
                            dialog.dismiss();
                            return;
                        } else if (startDate.compareTo(currentDate) < 0) {
                            edtNgayBDKM.setError("Ngày BD phải lớn hơn hoặc bằng hôm nay");
                            edtNgayBDKM.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            promotion.setStartDate(startDate);
                        }

                        if (edtNgayKTKM.getText().toString().isEmpty()) {
                            edtNgayKTKM.setError("Trường này là bắt buộc!");
                            edtNgayKTKM.requestFocus();
                            dialog.dismiss();
                            return;
                        } else if (!isValidDate(edtNgayKTKM.getText().toString())) {
                            edtNgayKTKM.setError("Ngày không hợp lệ");
                            edtNgayKTKM.requestFocus();
                            dialog.dismiss();
                            return;
                        } else if (startDate.compareTo(endDate) > 0) {
                            edtNgayKTKM.setError("Ngày KT phải lớn hơn hoặc bằng ngày BD");
                            edtNgayKTKM.requestFocus();
                            dialog.dismiss();
                            return;
                        } else {
                            promotion.setEndDate(convertStringToDate(edtNgayKTKM.getText().toString()));
                        }
                        promotion.setIdCategory(idCategory);
                        promotion.setImagePath(imagePath);

                        promotionDataSource.addPromotion(promotion);
                        Intent intent = new Intent(PromotionAddActivity.this, PromotionListActivity.class);
                        startActivity(intent);
                        Toast.makeText(PromotionAddActivity.this, "Thêm loại sản phẩm thành công", Toast.LENGTH_LONG).show();


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
        edtTenKM = findViewById(R.id.tenKM);
        edtLyDoKM = findViewById(R.id.lyDoKM);
        edtPhanTramKM = findViewById(R.id.phanTramKM);
        edtNgayBDKM = findViewById(R.id.ngayBDKM);
        edtNgayKTKM = findViewById(R.id.ngayKTKM);

        btnThem = findViewById(R.id.btnThem);
        btnBack = findViewById(R.id.btnBack);
        btnAnhKM = findViewById(R.id.btnAnhKM);
        btnNgayBDKM = findViewById(R.id.btnNgayBDKM);
        btnNgayKTKM = findViewById(R.id.btnNgayKTKM);

        imgAnhKM = findViewById(R.id.imgAnhKM);

        ivAnhLoaiSp = findViewById(R.id.ivAnhLoaiSp);

        spLoaiSp = findViewById(R.id.spLoaiSp);
    }

    public static Date setTimeToMidNight(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Đặt giờ, phút và giây thành 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }
}