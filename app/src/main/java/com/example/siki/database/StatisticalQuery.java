package com.example.siki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.StatisticalModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticalQuery {

    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;

    private StoreDataSource storeDataSource ;
    public  StatisticalQuery(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    public List<StatisticalModel> getProductSellMonthData(long id){
        String sql = "SELECT \n" +
                "    substr(createAt, 4, 2) AS month, \n" +
                "    substr(createAt, 7, 4) AS year, \n" +
                "    SUM(quantity) AS total_quantity \n" +
                "FROM \n" +
                "    \"Order\" o \n" +
                "JOIN \n" +
                "    OrderDetail od ON o.Id = od.order_id \n" +
                "WHERE \n" +
                "    od.product_id = ? \n" +
                "    AND o.status = 'Success' \n" +
                "GROUP BY \n" +
                "    month,\n" +
                "    year\n" +
                "ORDER BY \n" +
                "    year DESC, \n" +
                "    month DESC\n" +
                "LIMIT 6";
        List<StatisticalModel> listStatisticalModels = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor==null) return listStatisticalModels;
        if (cursor.moveToFirst()) {
            do {
                StatisticalModel statisticalModel = new StatisticalModel();
                statisticalModel.setTitle(cursor.getString(0)+"-"+cursor.getString(1));
                statisticalModel.setQuantity(cursor.getDouble(2));
                listStatisticalModels.add(statisticalModel);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        Collections.reverse(listStatisticalModels);
        return listStatisticalModels;
    }
    public List<StatisticalModel> getProductSellYearData(long id) {
        String sql = "SELECT \n" +
                "    substr(createAt, 7, 4) AS year, \n" +
                "    SUM(quantity) AS total_quantity \n" +
                "FROM \n" +
                "    \"Order\" o \n" +
                "JOIN \n" +
                "    OrderDetail od ON o.Id = od.order_id \n" +
                "WHERE \n" +
                "    od.product_id = ? \n" +
                "    AND o.status = 'Success' \n" +
                "GROUP BY \n" +
                "    year \n" +
                "ORDER BY \n" +
                "    year DESC \n" +
                "LIMIT \n" +
                "    5;";
        List<StatisticalModel> listStatisticalModels = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor==null) return listStatisticalModels;
        if (cursor.moveToFirst()) {
            do {
                StatisticalModel statisticalModel = new StatisticalModel();
                statisticalModel.setTitle(cursor.getString(0));
                statisticalModel.setQuantity( cursor.getDouble(1));
                listStatisticalModels.add(statisticalModel);
            }
            while (cursor.moveToNext());
        }
        Collections.reverse(listStatisticalModels);
        cursor.close();
        return listStatisticalModels;
    }

    public List<StatisticalModel> getProductRevenueYearData(long id) {
        String sql = "SELECT \n" +
                "    substr(createAt, 7, 4) AS year, \n" +
                "    SUM(quantity*price) AS total_quantity \n" +
                "FROM \n" +
                "    \"Order\" o \n" +
                "JOIN \n" +
                "    OrderDetail od ON o.Id = od.order_id \n" +
                "WHERE \n" +
                "    od.product_id = ? \n" +
                "    AND o.status = 'Success' \n" +
                "GROUP BY \n" +
                "    year \n" +
                "ORDER BY \n" +
                "    year DESC \n" +
                "LIMIT \n" +
                "    5;";
        List<StatisticalModel> listStatisticalModels = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor==null) return listStatisticalModels;
        if (cursor.moveToFirst()) {
            do {
                StatisticalModel statisticalModel = new StatisticalModel();
                statisticalModel.setTitle(cursor.getString(0));
                statisticalModel.setQuantity(cursor.getDouble(1));
                listStatisticalModels.add(statisticalModel);
            }
            while (cursor.moveToNext());
        }
        Collections.reverse(listStatisticalModels);
        cursor.close();
        return listStatisticalModels;
    }

    public List<StatisticalModel> getProductRevenueMonthData(long id){
        String sql = "SELECT \n" +
                "    substr(createAt, 4, 2) AS month, \n" +
                "    substr(createAt, 7, 4) AS year, \n" +
                "    SUM(quantity*price) AS total_quantity \n" +
                "FROM \n" +
                "    \"Order\" o \n" +
                "JOIN \n" +
                "    OrderDetail od ON o.Id = od.order_id \n" +
                "WHERE \n" +
                "    od.product_id = ? \n" +
                "    AND o.status = 'Success' \n" +
                "GROUP BY \n" +
                "    month,\n" +
                "    year\n" +
                "ORDER BY \n" +
                "    year DESC, \n" +
                "    month DESC\n" +
                "LIMIT 6";
        List<StatisticalModel> listStatisticalModels = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor==null) return listStatisticalModels;
        if (cursor.moveToFirst()) {
            do {
                StatisticalModel statisticalModel = new StatisticalModel();
                statisticalModel.setTitle(cursor.getString(0)+"-"+cursor.getString(1));
                statisticalModel.setQuantity(cursor.getDouble(2));
                listStatisticalModels.add(statisticalModel);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        Collections.reverse(listStatisticalModels);
        return listStatisticalModels;
    }

    public List<String> getAllMonthOrderSuccess(Long store_id) {
        String sql = "SELECT  \n" +
                "    substr(createAt, 4, 2) AS month, \n" +
                "    substr(createAt, 7, 4) AS year \n" +
                "FROM `Order` o\n" +
                "JOIN OrderDetail od ON o.Id = od.order_id\n" +
                "JOIN Product p ON od.product_id = p.Id\n" +
                "JOIN Store s ON p.StoreId = s.Id\n" +
                "WHERE o.status = 'Success' AND s.Id = ?" +
                "ORDER BY month DESC, year DESC\n" +
                "LIMIT 6;";
        List<String> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(store_id)});
        if (cursor.moveToFirst()){
            do {
                data.add(cursor.getString(0)+"-"+cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        Collections.reverse(data);
        return data;
    }

    public List<String> getAllYearOrderSuccess(Long store_id) {
        String sql = "SELECT  \n" +
                "    substr(createAt, 7, 4) AS year\n" +
                "FROM `Order` o\n" +
                "JOIN OrderDetail od ON o.Id = od.order_id\n" +
                "JOIN Product p ON od.product_id = p.Id\n" +
                "JOIN Store s ON p.StoreId = s.Id\n" +
                "WHERE o.status = 'Success' AND s.Id = ?" +
                "ORDER BY " +
                "date DESC\n" +
                "LIMIT 5;";
        List<String> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(store_id)});
        if (cursor.moveToFirst()){
            do {
                data.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        Collections.reverse(data);
        return data;
    }

    public List<Long> getAllIDProductStore(Long store_id) {
        String sql = "SELECT p.Id\n" +
                "FROM Product p\n" +
                "WHERE p.StoreId = ?;";
        List<Long> data = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(store_id)});
        if (cursor.moveToFirst()){
            do {
                data.add(cursor.getLong(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }
}
