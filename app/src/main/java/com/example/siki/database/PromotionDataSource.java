package com.example.siki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.Category;
import com.example.siki.model.Product;
import com.example.siki.model.Promotion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                promotion.setStartDate(convertStringToDate(cursor.getString(4)));
                promotion.setEndDate(convertStringToDate(cursor.getString(5)));
                promotion.setIdCategory(cursor.getInt(6));
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String startDate = sdf.format(promotion.getStartDate());
            String endDate = sdf.format(promotion.getEndDate());
            values.put("StartDate", startDate);
            values.put("EndDate", endDate);
            values.put("IdCategory", promotion.getIdCategory());
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String startDate = sdf.format(promotion.getStartDate());
            String endDate = sdf.format(promotion.getEndDate());
            values.put("StartDate", startDate);
            values.put("EndDate", endDate);
            values.put("IdCategory", promotion.getIdCategory());
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

    public Promotion findByIdCategory(long idCategory) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());

        Cursor cursor = null;
        Promotion promotion = new Promotion();

        String sql = "SELECT * FROM Promotion WHERE IdCategory = ? AND EndDate >= ?";
        cursor = db.rawQuery(sql, new String[]{String.valueOf(idCategory), currentDate});

        if (cursor != null && cursor.moveToFirst()) {
            promotion = new Promotion();
            promotion.setId(cursor.getLong(0));
            promotion.setName(cursor.getString(1));
            promotion.setReason(cursor.getString(2));
            promotion.setPercentPromotion(cursor.getInt(3));
            promotion.setStartDate(convertStringToDate(cursor.getString(4)));
            promotion.setEndDate(convertStringToDate(cursor.getString(5)));
            promotion.setIdCategory(cursor.getInt(6));
        }

        return promotion;
    }

    public static Date convertStringToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
