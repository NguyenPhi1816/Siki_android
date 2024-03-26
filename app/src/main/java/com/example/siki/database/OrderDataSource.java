package com.example.siki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.siki.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Calendar;

public class OrderDataSource {
    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;


    public OrderDataSource(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Long createOrder (String receiverPhoneNumber,
                             String receiverAddress,
                             String receiverName,
                             String note,
                             int userId
    ) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("receiverPhoneNumber", receiverPhoneNumber);
            values.put("receiverAddress", receiverAddress);
            values.put("receiverName", receiverName);
            values.put("status", OrderStatus.Pending.toString());
            values.put("createdAt", Calendar.getInstance().getTime().toString());
            values.put("note", note);
            values.put("user_id", userId);
            id = db.insert("Order", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
