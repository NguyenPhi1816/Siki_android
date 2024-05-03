package com.example.siki.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.Account;

public class AccountDataSource {
    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;

    public AccountDataSource(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put("PhoneNumber", account.getPhoneNumber());
        values.put("Password", account.getPassword());
        values.put("Role", account.getRole());
        values.put("Status", account.getStatus());

        return db.insert("Account", null, values);
    }

    @SuppressLint("Range")
    public Account getAccountByPhoneNumber(String phoneNumber) {
        Cursor cursor = db.query("Account", null, "PhoneNumber=?", new String[]{phoneNumber}, null, null, null);
        Account account = null;
        if (cursor != null && cursor.moveToFirst()) {
            account = new Account();
            account.setPhoneNumber(cursor.getString(cursor.getColumnIndex("PhoneNumber")));
            account.setPassword(cursor.getString(cursor.getColumnIndex("Password")));
            account.setRole(cursor.getString(cursor.getColumnIndex("Role")));
            account.setStatus(cursor.getString(cursor.getColumnIndex("Status")));
        }
        if (cursor != null) {
            cursor.close();
        }
        return account;
    }

    public int updateAccount(Account account) {
        ContentValues values = new ContentValues();
        values.put("Password", account.getPassword());
        values.put("Role", account.getRole());
        values.put("Status", account.getStatus());

        return db.update("Account", values, "PhoneNumber=?", new String[]{account.getPhoneNumber()});
    }

    public int deleteAccount(String phoneNumber) {
        return db.delete("Account", "PhoneNumber=?", new String[]{phoneNumber});
    }
}
