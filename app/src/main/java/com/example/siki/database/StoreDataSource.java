package com.example.siki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.Product;
import com.example.siki.model.Store;

public class StoreDataSource {
    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;

    public StoreDataSource(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Store findById(Long storeId) {
        Store store = null;
        try (Cursor cursor = db.query("Store", null, "Id=?", new String[]{String.valueOf(storeId)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                store = new Store();
                store.setId(cursor.getLong(0));
                store.setName(cursor.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    public Long createStore(Long id,
                            String name,
                            Long userId,
                            String description,
                            String avatar,
                            String status,
                            String backgroundImage) {
        Long code = -1L;
        try {
            ContentValues values = new ContentValues();
            values.put("Id", id);
            values.put("Name", name);
            values.put("UserId", userId);
            values.put("Description", description);
            values.put("Avatar", avatar);
            values.put(" BackgroundImage", backgroundImage);
            values.put("Status", status);
            code = db.insert("Store", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return code;
    }
}
