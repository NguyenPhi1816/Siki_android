package com.example.siki.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.siki.enums.OrderStatus;
import com.example.siki.model.Category;
import com.example.siki.model.Order;
import com.example.siki.model.Product;
import com.example.siki.model.ProductCategory;
import com.example.siki.model.Promotion;
import com.example.siki.model.User;
import com.example.siki.utils.DateFormatter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Long findCategoryByProduct (Long productId) {
        String sql = "Select category_id from ProductCategory Where product_id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(productId)});
        if (cursor.moveToFirst()) {
            Long categoryId = cursor.getLong(0);
            return categoryId;
        }
        return -1L;
    }


    public List<Product> findByCategoryId(Long categoryId, PromotionDataSource promotionDataSource) {
        List<Product> productList = new ArrayList<>();
        String sql = "select p.Id, p.Name, p.ImagePath, p.ProductPrice, p.Quantity " +
                "from Product p " +
                "inner join ProductCategory pc  " +
                "on p.id = pc.ProductId " +
                "where pc.CategoryId = ?";;

        try {
            // Thực thi truy vấn
            Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(categoryId)});

            // Lấy tên danh mục nếu có kết quả
            if (cursor.moveToFirst()) {
                do {
                    Product product = new Product();
                    Long productId = cursor.getLong(0);

                    Promotion promotion = promotionDataSource.findByIdCategory(categoryId);
                    Double price = cursor.getDouble(3);
                    if (promotion != null) {
                        int percent = promotion.getPercentPromotion();
                        Double newPrice = price - price * percent / 100;
                        product.setPrice(newPrice);
                    }else {
                        product.setPrice(price);
                    }
                    product.setId(productId);
                    product.setName(cursor.getString(1));
                    product.setImagePath(cursor.getString(2));
                    product.setOldPrice(price);
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
