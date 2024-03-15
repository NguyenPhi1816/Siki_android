package com.example.siki.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.Category;
import com.example.siki.model.Product;
import com.example.siki.model.ProductCategory;
import com.example.siki.model.User;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDatabase {
    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;
    public ProductCategoryDatabase(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    @SuppressLint("Range")
    public List<String> findNameCategoryByProductId(int productId) {
        List<String> categoryNameList = new ArrayList<>();
        try (Cursor cursor = db.query("ProductCategory", null, "ProductId=?", new String[]{String.valueOf(productId)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    categoryNameList.add(cursor.getString(cursor.getColumnIndex("Name")));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return categoryNameList;
    }

    public long addProductCategory(ProductCategory productCategory) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Id", productCategory.getId());
            values.put("ProductId", productCategory.getProductId());
            values.put("CategoryId", productCategory.getCategoryId());

            id = db.insert("ProductCategory", null, values);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return id;
    }

    private boolean findByProduct() {

    }

    public int deleteProductCategory(ProductCategory productCategory) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("ProductCategory", "Id=?", new String[]{String.valueOf(productCategory.getId())});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
