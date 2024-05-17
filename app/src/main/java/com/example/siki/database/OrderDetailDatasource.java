package com.example.siki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.Cart;
import com.example.siki.model.Order;
import com.example.siki.model.OrderDetail;
import com.example.siki.model.Product;
import com.example.siki.model.User;

import java.util.ArrayList;
import java.util.List;

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
            id = db.insert("OrderDetail", null, values);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return id;
    }


    public List<OrderDetail> findByOrderId(Long orderId, ProductDatabase productDatabase) {
        String sql = "Select * from OrderDetail od where od.order_id = ?";
        List<OrderDetail> orderDetails = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(orderId)});
        if (cursor.moveToFirst()) {
            do {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setId(cursor.getLong(0));
                orderDetail.setQuantity(cursor.getInt(3));
                orderDetail.setPrice(cursor.getDouble(4));
                Long productId = cursor.getLong(2);
                Product product = productDatabase.findById(productId);
                orderDetail.setProduct(product);
                orderDetails.add(orderDetail);
            } while (cursor.moveToNext());
        }
        return orderDetails;
    }

    public int deleteByOrder(Long orderId) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("`OrderDetail`", "order_id=?", new String[]{String.valueOf(orderId)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;

    }
}
