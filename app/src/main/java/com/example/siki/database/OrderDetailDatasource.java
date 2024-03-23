package com.example.siki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class OrderDetailDatasource {

    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;


    public OrderDetailDatasource(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long save (Long product_id, Long order_id, int quantity, double price) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("order_id", order_id);
            values.put("product_id", product_id);
            values.put("quantity",quantity );
            values.put("price", price);
            id = db.insert("Cart", null, values);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return id;
    }
}
