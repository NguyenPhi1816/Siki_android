package com.example.siki.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;


import com.example.siki.enums.OrderStatus;
import com.example.siki.model.Order;
import com.example.siki.model.Product;
import com.example.siki.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.US);
                Date date = null;
                try {
                    date = dateFormat.parse(createdAt);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                order.setCreatedAt(localDateTime);
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
    @SuppressLint("NewApi")
    public List<Order> findAllByUserId(UserDataSource userDataSource,
                               ProductDatabase productDatabase,
                               OrderDetailDatasource orderDetailDatasource,
                                       int userId) {
        String sql = "Select * from `Order` Where user_id = ?";
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
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.US);
                Date date = null;
                try {
                    date = dateFormat.parse(createdAt);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
                order.setCreatedAt(localDateTime);
                order.setStatus(OrderStatus.valueOf(cursor.getString(6)));
                order.setOrderDetails(orderDetailDatasource.findByOrderId(orderId, productDatabase));
                User user = userDataSource.getUserById(cursor.getInt(7));
                order.setUser(user);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
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

    public int updateOrder(Long id, Order order) {
        int rowsAffected = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("note", order.getNote());
            values.put("receiverName", order.getReceiverName());
            values.put("receiverPhoneNumber", order.getReceiverPhoneNumber());
            values.put("receiverAddress", order.getReceiverAddress());
            values.put("status", order.getStatus().toString());
            rowsAffected = db.update("`Order`", values, "Id=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
    public int deleteOrder(Long orderId) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("`Order`", "Id=?", new String[]{String.valueOf(orderId)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
