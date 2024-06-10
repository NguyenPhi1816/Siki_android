package com.example.siki.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDatabase {
    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;
    public CategoryDatabase(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public List<Category> readDb() {
        String sql = "Select * from Category";
        List<Category> listCategory = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                category.setDescription(cursor.getString(2));
                category.setImage(cursor.getString(3));
                listCategory.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listCategory;
    }
    public Category findById(Integer categoryId) {
        Category category = new Category();
        try (Cursor cursor = db.query("Category", null, "Id=?", new String[]{String.valueOf(categoryId)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                category.setId(cursor.getInt(0));
                category.setName(cursor.getString(1));
                category.setDescription(cursor.getString(2));
                category.setImage(cursor.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    @SuppressLint("Range")
    public String findImagePathById(int id) {
        String rs = "";
        try {
            Cursor cursor = db.query("Category", null, "Id=?", new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                rs = cursor.getString(cursor.getColumnIndex("ImagePath"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public Map<Integer, String> getAllCategory() {
        String sql = "Select * from Category";
        Map<Integer, String> listCategory = new HashMap<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                listCategory.put(cursor.getInt(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listCategory;
    }

    public long addCategory(Category category) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Name", category.getName());
            values.put("Description", category.getDescription());
            values.put("ImagePath", category.getImage());

            id = db.insert("Category", null, values);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return id;
    }

    public int deleteProduct(Category category) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("Category", "Id=?", new String[]{String.valueOf(category.getId())});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public int updateCategory(Category category) {
        int rowsAffected = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Name", category.getName());
            values.put("Description", category.getDescription());
            values.put("ImagePath", category.getImage());

            rowsAffected = db.update("Category", values, "Id=?", new String[]{String.valueOf(category.getId())});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }

}
