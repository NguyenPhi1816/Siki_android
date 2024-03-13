package com.example.siki.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.siki.R;
import com.example.siki.database.DatabaseProduct;
import com.example.siki.model.Product;
import com.example.siki.model.ProductPrice;

public class ProductAddActivity extends AppCompatActivity {
    EditText edtTenSp, edtMaSp, edtGiaSp, edtAnhSp;
    Button btnBack, btnThem;
    DatabaseProduct databaseProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        setControl();
        setEvent();

    }

    private void setEvent() {
        databaseProduct = new DatabaseProduct(this);
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
                        databaseProduct.addProduct(product);
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
    }
}