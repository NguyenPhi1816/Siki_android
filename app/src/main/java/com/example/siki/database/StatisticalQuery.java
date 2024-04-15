package com.example.siki.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.StatisticalModel;

import java.util.ArrayList;
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
                "    strftime('%Y', createAt) AS year, \n" +
                "    strftime('%m', createAt) AS month, \n" +
                "    SUM(quantity) AS total_quantity \n" +
                "FROM \n" +
                "    \"Order\" o \n" +
                "JOIN \n" +
                "    OrderDetail od ON o.Id = od.order_id \n" +
                "WHERE \n" +
                "    od.product_id = ? \n" +
                "    AND o.status = 'Đã bán' \n" +
                "GROUP BY \n" +
                "    strftime('%Y', createAt), \n" +
                "    strftime('%m', createAt) \n" +
                "ORDER BY \n" +
                "    year DESC, \n" +
                "    month DESC \n" +
                "LIMIT \n" +
                "    6;\n";
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
        return listStatisticalModels;
    }
    public List<StatisticalModel> getProductSellYearData(int id) {
        String sql = "SELECT \n" +
                "    strftime('%Y', createAt) AS year, \n" +
                "    SUM(quantity) AS total_quantity \n" +
                "FROM \n" +
                "    \"Order\" o \n" +
                "JOIN \n" +
                "    OrderDetail od ON o.Id = od.order_id \n" +
                "WHERE \n" +
                "    od.product_id = ? \n" +
                "    AND o.status = 'Đã bán' \n" +
                "GROUP BY \n" +
                "    strftime('%Y', createAt) \n" +
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
        cursor.close();
        return listStatisticalModels;
    }
}
