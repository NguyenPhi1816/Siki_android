package com.example.siki.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

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
    public List<String> findNameCategoryByProductId(Long productId) {
        List<String> categoryNameList = new ArrayList<>();
        String sql = "SELECT Category.Name AS category_name " +
                "FROM ProductCategory " +
                "INNER JOIN Category ON ProductCategory.CategoryId = Category.id " +
                "WHERE ProductCategory.ProductId = ?";
        try {
            // Thực thi truy vấn
            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(productId)});

            // Lấy tên danh mục nếu có kết quả
            if (cursor.moveToFirst()) {
                do {
                    categoryNameList.add(cursor.getString(cursor.getColumnIndex("category_name")));
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return categoryNameList;
    }

    public List<Product> findByCategoryId(Integer categoryI) {
        List<Product> productList = new ArrayList<>();
        String sql = "select p.Id, p.Name, p.ImagePath, p.ProductPrice, p.Quantity " +
                "from ProductCategory pc " +
                "inner join Product p  " +
                "on p.id = pc.product_id " +
                "where pc.category_id = ?";;

        try {
            // Thực thi truy vấn
            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(categoryI)});

            // Lấy tên danh mục nếu có kết quả
            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    product.setId(cursor.getLong(0));
                    product.setName(cursor.getString(1));
                    product.setImagePath(cursor.getString(2));
                    product.setPrice(cursor.getDouble(3));
                    product.setQuantity(cursor.getInt(4));
                    productList.add(product);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return productList  ;
    }

    public long addProductCategory(ProductCategory productCategory) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("ProductId", productCategory.getProductId());
            values.put("CategoryId", productCategory.getCategoryId());

            id = db.insert("ProductCategory", null, values);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return id;
    }

    public boolean isIdExists(int id) {
        try {
            Cursor cursor = db.query("ProductCategory", null, "Id=?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                return true;
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return false;
    }

    public int deleteByProductId(Long id) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("ProductCategory", "ProductId=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
