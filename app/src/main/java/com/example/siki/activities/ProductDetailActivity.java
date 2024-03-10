package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.siki.R;
import com.example.siki.database.DatabaseProduct;
import com.example.siki.model.Product;
import com.example.siki.model.ProductPrice;

public class ProductDetailActivity extends AppCompatActivity {
    EditText edtTenSp, edtMaSp, edtGiaSp, edtAnhSp;
    Button btnBack, btnSua;
    DatabaseProduct databaseProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setControl();
        setEvent();

    }

    private void setEvent() {
        databaseProduct = new DatabaseProduct(this);
        Product product = (Product) getIntent().getSerializableExtra("product");
        if (product != null) {
            if (product.getProductPrice() != null) {
                edtGiaSp.setText(product.getProductPrice().getPrice().toString());
            } else {
                edtGiaSp.setText("0");
            }
            if (product.getId() != null) {
                edtMaSp.setText(product.getId().toString());
            } else {
                edtMaSp.setText("0");
            }
            edtTenSp.setText(product.getName());
            edtAnhSp.setText(product.getImagePath());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(ProductDetailActivity.this, ProductListActivity.class);
                ProductDetailActivity.this.startActivity(activityChangeIntent);
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = new Product();
                ProductPrice productPrice = new ProductPrice();
                productPrice.setPrice(Double.parseDouble(edtGiaSp.getText().toString()));
                product.setId(Long.parseLong(edtMaSp.getText().toString()));
                product.setImagePath(edtAnhSp.getText().toString());
                product.setName(edtTenSp.getText().toString());
                product.setProductPrice(productPrice);
                databaseProduct.updateProduct(product);
                Intent intent = new Intent(ProductDetailActivity.this, ProductListActivity.class);
                startActivity(intent);
                Toast.makeText(ProductDetailActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setControl() {
        edtTenSp = findViewById(R.id.tenSp);
        edtGiaSp = findViewById(R.id.giaSp);
        edtAnhSp = findViewById(R.id.anhSP);
        edtMaSp = findViewById(R.id.maSp);

        btnSua = findViewById(R.id.btnSua);
        btnBack = findViewById(R.id.btnBack);
    }
}