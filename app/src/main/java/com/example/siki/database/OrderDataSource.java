package com.example.siki.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;


import com.example.siki.enums.OrderStatus;
import com.example.siki.model.Order;
import com.example.siki.model.OrderDetail;
import com.example.siki.model.Product;
import com.example.siki.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
            values.put("createAt", Calendar.getInstance().getTime().toString());
            values.put("note", note);
            values.put("user_id", userId);
            id = db.insert("`Order`", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
  /*  "    receiverPhoneNumber TEXT,\n" +
            "    receiverAddress TEXT,\n" +
            "    receiverName TEXT,\n" +
            "    note TEXT,\n" +
            "    createAt TEXT,\n" +
            "    status TEXT,\n" +
            "    user_id INTEGER,\n" +*/
    @SuppressLint("NewApi")
    public List<Order> findAll(UserDataSource userDataSource,
                               ProductDatabase productDatabase,
                               OrderDetailDatasource orderDetailDatasource) {
        String sql = "Select * from `Order`";
        List<Order> orders = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                Long orderId = cursor.getLong(0);
                order.setId(orderId);
                order.setReceiverPhoneNumber(cursor.getString(1));
                order.setReceiverAddress(cursor.getString(2));
                order.setReceiverName(cursor.getString(3));
                order.setNote(cursor.getString(4));
                String createdAt = cursor.getString(5);
                /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Parse the string to LocalDateTime
                LocalDateTime localDateTime = LocalDateTime.parse(createdAt, formatter);*/
                order.setCreatedAt(LocalDateTime.of(2024,12,12, 12,12));
                order.setStatus(OrderStatus.valueOf(cursor.getString(6)));
                order.setOrderDetails(orderDetailDatasource.findByOrderId(orderId, productDatabase));
                int userId = cursor.getInt(7);
                User user = userDataSource.getUserById(userId);
                order.setUser(user);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

    @SuppressLint("NewApi")
    public Optional<Order> findById(OrderDetailDatasource orderDetailDatasource, ProductDatabase productDatabase, UserDataSource userDataSource, Long orderId) {
        String sql = "Select * from `Order` Where Id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(orderId)});
        if (cursor.moveToFirst()) {
            Order order = new Order();
            order.setId(orderId);
            order.setReceiverPhoneNumber(cursor.getString(1));
            order.setReceiverAddress(cursor.getString(2));
            order.setReceiverName(cursor.getString(3));
            order.setNote(cursor.getString(4));
            String createdAt = cursor.getString(5);
                /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Parse the string to LocalDateTime
                LocalDateTime localDateTime = LocalDateTime.parse(createdAt, formatter);*/
            order.setCreatedAt(LocalDateTime.of(2024,12,12, 12,12));
            order.setStatus(OrderStatus.valueOf(cursor.getString(6)));
            order.setOrderDetails(orderDetailDatasource.findByOrderId(orderId, productDatabase));
            int userId = cursor.getInt(7);
            User user = userDataSource.getUserById(userId);
            order.setUser(user);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    public Long createOrder (String receiverPhoneNumber,
                             String receiverAddress,
                             String receiverName,
                             String note,
                             String createAt,
                             String status,
                             int userId
    ) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("receiverPhoneNumber", receiverPhoneNumber);
            values.put("receiverAddress", receiverAddress);
            values.put("receiverName", receiverName);
            values.put("status", status);
            values.put("createAt", createAt);
            values.put("note", note);
            values.put("user_id", userId);
            id = db.insert("`Order`", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @SuppressLint("NewApi")
    public List<Order> findByUserId (OrderDetailDatasource orderDetailDatasource, ProductDatabase productDatabase, UserDataSource userDataSource, int userId) {
        String sql = "Select * from 'Order' ord where ord.user_id = ?";

        List<Order> orders = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                Long orderId = cursor.getLong(0);
                order.setId(orderId);
                order.setReceiverPhoneNumber(cursor.getString(1));
                order.setReceiverAddress(cursor.getString(2));
                order.setReceiverName(cursor.getString(3));
                order.setNote(cursor.getString(4));
                String createdAt = cursor.getString(5);
                /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Parse the string to LocalDateTime
                LocalDateTime localDateTime = LocalDateTime.parse(createdAt, formatter);*/
                order.setCreatedAt(LocalDateTime.of(2024,12,12, 12,12));
                order.setStatus(OrderStatus.valueOf(cursor.getString(6)));
                order.setOrderDetails(orderDetailDatasource.findByOrderId(orderId, productDatabase));
                User user = userDataSource.getUserById(userId);
                order.setUser(user);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }
}
