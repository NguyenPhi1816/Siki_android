package com.example.siki.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CategoryDatabase extends SQLiteOpenHelper {
    public CategoryDatabase(@Nullable Context context) {
        super(context, "db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table tbProduct(id text, name text, imagePath text, productPrice text)";
        sql += "create table tbUser(id text, firstName text, lastName text, address text, phoneNumber text, gender text, dateOfBirth text, avatar text, email text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
