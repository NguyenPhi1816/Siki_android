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

    public List<StatisticalModel> getProductSellMonthData(int id){
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
                statisticalModel.setQuantity((double) cursor.getInt(2));
                listStatisticalModels.add(statisticalModel);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        Collections.reverse(listStatisticalModels);
        return listStatisticalModels;
    }
    public List<StatisticalModel> getProductSellYearData(int id) {
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
                statisticalModel.setQuantity((double) cursor.getInt(1));
                listStatisticalModels.add(statisticalModel);
            }
            while (cursor.moveToNext());
        }
        Collections.reverse(listStatisticalModels);
        cursor.close();
        return listStatisticalModels;
    }
}
