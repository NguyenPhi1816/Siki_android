package com.example.siki.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SikiDatabaseHelper extends SQLiteOpenHelper {
    private static final String databaseName = "SIKI_DB";
    private static final int databaseVersion = 1;

    public SikiDatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        SQLiteDatabase db= this.getWritableDatabase();
        createTables(db);
        Log.i("DB", "dbManager");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DB", "dbOnCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTables (SQLiteDatabase db) {
        db.execSQL(createUserTable());
        db.execSQL(createAccountTable());
        db.execSQL(createProductTable());
        db.execSQL(createCartTable());
        db.execSQL(createCategoryTable());
        db.execSQL(createProductCategoryTable());
        db.execSQL(createStoreTable());

    }

    // Define methods to create each table
    private String createUserTable() {
        System.out.println("User table was created.");
        return "CREATE TABLE IF NOT EXISTS User (" +
                "Id INTEGER PRIMARY KEY, " +
                "FirstName TEXT, " +
                "LastName TEXT, " +
                "Address TEXT, " +
                "PhoneNumber TEXT, " +
                "Gender TEXT, " +
                "DateOfBirth TEXT, " +
                "Avatar TEXT, " +
                "Email TEXT);";
    }

    public String createAccountTable() {
        System.out.println("Account table was created.");
        return "CREATE TABLE IF NOT EXISTS Account (" +
                "PhoneNumber TEXT PRIMARY KEY, " +
                "Password TEXT, " +
                "UserRoleId INTEGER, " +
                "Status TEXT);";
    }

    public String createStoreTable() {
        return "PRAGMA foreign_keys = ON;\n" +
                "\n" +
                "CREATE TABLE Store (\n" +
                "    Id INTEGER PRIMARY KEY,\n" +
                "    Name TEXT,\n" +
                "    UserId INTEGER,\n" +
                "    Description TEXT,\n" +
                "    Avatar TEXT,\n" +
                "    BackgroundImage TEXT,\n" +
                "    Status TEXT,\n" +
                "    FOREIGN KEY (UserId) REFERENCES User(id)\n" +
                ");\n";
    }

    private String createProductTable() {
        return "create table if not exists Product(Id integer primary key autoincrement, Name text, ImagePath text, ProductPrice double, Quantity integer, StoreId integer)";
    }

    private String createCategoryTable() {
        return "CREATE TABLE IF NOT EXISTS Category (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Description TEXT)";
    }

    private String createProductCategoryTable() {
        return "CREATE TABLE IF NOT EXISTS ProductCategory (" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ProductId INTEGER," +
                "CategoryId INTEGER)";
    }

    private String createCartTable() {
        return "CREATE TABLE IF NOT EXISTS Cart (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    user_id BIGINT,\n" +
                "    product_id BIGINT,\n" +
                "    quantity INTEGER,\n" +
                "    is_selected BOOLEAN,\n" +
                "    FOREIGN KEY (user_id) REFERENCES User(Id),\n" +
                "    FOREIGN KEY (product_id) REFERENCES Product(Id)\n" +
                ");\n";
    }
}
