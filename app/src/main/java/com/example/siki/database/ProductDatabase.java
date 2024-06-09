package com.example.siki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.Product;
import com.example.siki.model.StatisticalModel;
import com.example.siki.model.Store;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;

    private StoreDataSource storeDataSource ;
    public ProductDatabase(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Product> readDb() {
        String sql = "Select * from Product";
        List<Product> listProduct = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getLong(0));
                product.setName(cursor.getString(1));
                product.setImagePath(cursor.getString(2));
                product.setPrice(cursor.getDouble(3));
                product.setQuantity(cursor.getInt(4));
                listProduct.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listProduct;
    }

    public List<Product> readTop10New() {
        String sql = "Select * from Product as pd Order by pd.Id DESC LIMIT 10";
        List<Product> listProduct = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getLong(0));
                product.setName(cursor.getString(1));
                product.setImagePath(cursor.getString(2));
                product.setPrice(cursor.getDouble(3));
                product.setQuantity(cursor.getInt(4));
                listProduct.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listProduct;
    }


    public Product findById(Long productId) {
        Product product = null;
        Store store1 = new Store();
        store1.setId(1L);
        store1.setName("Samsung");
        Store store2 = new Store();
        store2.setId(2L);
        store2.setName("Apple");
        try (Cursor cursor = db.query("Product", null, "id=?", new String[]{String.valueOf(productId)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                product = new Product();
                product.setId(cursor.getLong(0));
                product.setName(cursor.getString(1));
                product.setImagePath(cursor.getString(2));
                product.setPrice(cursor.getDouble(3));
                product.setQuantity(cursor.getInt(4));
                Long storeId = cursor.getLong(5);
                if (storeId == 1) {
                    product.setStore(store1);
                }else if (storeId == 2) {
                    product.setStore(store2);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public Long addProduct(Product product) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Id", product.getId());
            values.put("Name", product.getName());
            values.put("ImagePath", product.getImagePath());
            values.put("ProductPrice", product.getPrice());
            values.put("Quantity", product.getQuantity());
            values.put("StoreId", 2);

            id = db.insert("Product", null, values);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        System.out.println(id);
        return id;
    }

    public int deleteProduct(Product product) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("Product", "Id=?", new String[]{String.valueOf(product.getId())});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public int updateProduct(Product product) {
        int rowsAffected = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Name", product.getName());
            values.put("ImagePath", product.getImagePath());
            values.put("ProductPrice", product.getPrice());
            values.put("Quantity", product.getQuantity());
            values.put("StoreId", 2);

            rowsAffected = db.update("Product", values, "Id=?", new String[]{String.valueOf(product.getId())});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public int updateQuantityProduct(Long productId, int quantity) {
        int rowsAffected = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Quantity", quantity);
            rowsAffected = db.update("Product", values, "Id=?", new String[]{String.valueOf(productId)});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
