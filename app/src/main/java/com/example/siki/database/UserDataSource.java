package com.example.siki.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.siki.model.User;

public class UserDataSource {
    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;

    public UserDataSource(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertUser(User user) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("FirstName", user.getFirstName());
            values.put("LastName", user.getLastName());
            values.put("Address", user.getAddress());
            values.put("PhoneNumber", user.getPhoneNumber());
            values.put("Gender", user.getGender());
            values.put("DateOfBirth", user.getDateOfBirth());
            values.put("Avatar", user.getAvatar());
            values.put("Email", user.getEmail());

            id = db.insert("User", null, values);
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return id;
    }

    @SuppressLint("Range")
    public User getUserById(int userId) {
        User user = null;
        try (Cursor cursor = db.query("User", null, "Id=?", new String[]{String.valueOf(userId)}, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                user.setFirstName(cursor.getString(cursor.getColumnIndex("FirstName")));
                user.setLastName(cursor.getString(cursor.getColumnIndex("LastName")));
                user.setAddress(cursor.getString(cursor.getColumnIndex("Address")));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("PhoneNumber")));
                user.setGender(cursor.getString(cursor.getColumnIndex("Gender")));
                user.setDateOfBirth(cursor.getString(cursor.getColumnIndex("DateOfBirth")));
                user.setAvatar(cursor.getString(cursor.getColumnIndex("Avatar")));
                user.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return user;
    }

    public int updateUser(User user) {
        int rowsAffected = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("FirstName", user.getFirstName());
            values.put("LastName", user.getLastName());
            values.put("Address", user.getAddress());
            values.put("PhoneNumber", user.getPhoneNumber());
            values.put("Gender", user.getGender());
            values.put("DateOfBirth", user.getDateOfBirth());
            values.put("Avatar", user.getAvatar());
            values.put("Email", user.getEmail());

            rowsAffected = db.update("User", values, "Id=?", new String[]{String.valueOf(user.getId())});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public int deleteUser(int userId) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("User", "Id=?", new String[]{String.valueOf(userId)});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
