package com.example.siki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.Category;
import com.example.siki.model.Product;
import com.example.siki.model.Promotion;

import java.util.ArrayList;
import java.util.List;

public class PromotionDataSource {
    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;
    public PromotionDataSource(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Promotion> readDb() {
        String sql = "Select * from Promotion";
        List<Promotion> listPromotion = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Promotion promotion = new Promotion();
                promotion.setId(cursor.getLong(0));
                promotion.setName(cursor.getString(1));
                promotion.setReason(cursor.getString(2));
                promotion.setPercentPromotion(cursor.getInt(3));
                promotion.setStartDate(cursor.getString(4));
                promotion.setEndDate(cursor.getString(5));
                promotion.setNameCategory(cursor.getString(6));
                promotion.setImagePath(cursor.getString(7));
                listPromotion.add(promotion);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listPromotion;
    }

    public int updatePromotion(Promotion promotion) {
        int rowsAffected = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Name", promotion.getName());
            values.put("Reason", promotion.getReason());
            values.put("PercentPromotion", promotion.getPercentPromotion());
            values.put("StartDate", promotion.getStartDate());
            values.put("EndDate", promotion.getEndDate());
            values.put("NameCategory", promotion.getNameCategory());
            values.put("ImagePath", promotion.getImagePath());

            rowsAffected = db.update("Promotion", values, "Id=?", new String[]{String.valueOf(promotion.getId())});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public long addPromotion(Promotion promotion) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("Name", promotion.getName());
            values.put("Reason", promotion.getReason());
            values.put("PercentPromotion", promotion.getPercentPromotion());
            values.put("StartDate", promotion.getStartDate());
            values.put("EndDate", promotion.getEndDate());
            values.put("NameCategory", promotion.getNameCategory());
            values.put("ImagePath", promotion.getImagePath());
            id = db.insert("Promotion", null, values);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return id;
    }

    public int deletePromotion(Promotion promotion) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("Promotion", "Id=?", new String[]{String.valueOf(promotion.getId())});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }

}
