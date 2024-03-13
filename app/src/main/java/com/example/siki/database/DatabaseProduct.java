package com.example.siki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.siki.model.Product;
import com.example.siki.model.ProductPrice;

import java.util.ArrayList;
import java.util.List;

public class DatabaseProduct extends SQLiteOpenHelper {

    public DatabaseProduct(@Nullable Context context) {
        super(context, "dbProduct", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table tbProduct(id text, name text, imagePath text, productPrice text)";
        sql += "create table tbUser(id text, firstName text, lastName text, address text, phoneNumber text, gender text, dateOfBirth text, avatar text, email text)";
        db.execSQL(sql);
    }

    public List<Product> readDb() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "Select * from tbProduct";
        List<Product> listProduct = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getLong(0));
                product.setName(cursor.getString(1));
                product.setImagePath(cursor.getString(2));
                ProductPrice productPrice = new ProductPrice();
                productPrice.setPrice(cursor.getDouble(3));
                product.setProductPrice(productPrice);
                listProduct.add(product);
            } while (cursor.moveToNext());
        }
        return listProduct;
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "insert into tbProduct values(?,?,?,?)";
        db.execSQL(sql, new String[] {product.getId().toString(), product.getName(), product.getImagePath(), product.getProductPrice().getPrice().toString()});
    }

    public void deleteProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "delete from tbProduct where id=?";
        db.execSQL(sql, new String[] {product.getId().toString()});
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "update tbProduct set name=?, imagePath=?, productPrice=? where id=?";
        db.execSQL(sql, new String[] {product.getName(), product.getImagePath(), product.getProductPrice().getPrice().toString(), product.getId().toString()});
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
