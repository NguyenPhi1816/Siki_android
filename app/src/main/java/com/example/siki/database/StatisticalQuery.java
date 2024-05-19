package com.example.siki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.StatisticalModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<String> getAllMonthOrderSuccess() {
        String sql = "SELECT  \n" +
                "    substr(createAt, 4, 7) AS monthyear \n" +
                "FROM `Order` o\n" +
                "WHERE o.status = 'Success'";
        Set<String> dataset = new HashSet<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            do {
                dataset.add(cursor.getString(0));

            } while (cursor.moveToNext());
        }
        List<String> data = new ArrayList<>(dataset);
        cursor.close();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        Collections.sort(data, new Comparator<String>() {
            @Override
            public int compare(String dateStr1, String dateStr2) {
                try {
                    Date date1 = dateFormat.parse(dateStr1);
                    Date date2 = dateFormat.parse(dateStr2);
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        Collections.reverse(data);
        return data.size()>6?data.subList(0,6):data;
    }

    public List<StatisticalModel> getProductSoldByMonth(String month){
        String sql = "SELECT\n" +
                "    p.Name AS ProductName,\n" +
                "    SUM(od.quantity) AS TotalQuantity\n" +
                "FROM `Order` o\n" +
                "JOIN OrderDetail od ON o.Id = od.order_id\n" +
                "JOIN Product p ON od.product_id = p.Id\n" +
                "WHERE o.status = 'Success'\n" +
                "  AND substr(o.createAt, 4, 7) = ?\n" +
                "GROUP BY p.Name\n" +
                "ORDER BY ProductName DESC;";
        Cursor cursor = db.rawQuery(sql, new String[]{month});
        List<StatisticalModel> data = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                data.add(new StatisticalModel(cursor.getString(0),cursor.getDouble(1)));
            } while (cursor.moveToNext());
        }
        return data;
    }
}
