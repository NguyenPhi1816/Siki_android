package com.example.siki.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.siki.model.Cart;
import com.example.siki.model.Product;
import com.example.siki.model.User;

import java.util.ArrayList;
import java.util.List;

public class CartDatasource {
    private SQLiteDatabase db;
    private SikiDatabaseHelper dbHelper;

    public CartDatasource(Context context) {
        dbHelper = new SikiDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public List<Cart> findByUser(int userId, ProductDatabase productDatabase, UserDataSource userDataSource) {
        String sql = "Select * from Cart c where c.user_id = ?";
        List<Cart> listCart = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setId(cursor.getLong(0));
                cart.setQuantity(cursor.getInt(3));
                Long productId = cursor.getLong(2);
                int isSelectedInt = cursor.getInt(4); // Assuming is_selected field is stored as an integer (0 or 1)
                boolean isChosen = isSelectedInt == 1;
                cart.setChosen(isChosen);
                Product product = productDatabase.findById(productId);
                cart.setProduct(product);
                User user = userDataSource.getUserById(userId);
                cart.setUser(user);
                listCart.add(cart);
            } while (cursor.moveToNext());
        }
        return listCart;
    }

    public Cart findByProductAndUser(Long productId, int userId, UserDataSource userDataSource, ProductDatabase productDatabase) {
        String sql = "Select * from Cart c where c.user_id = ? and c.product_id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(userId), String.valueOf(productId)});
        if (cursor.moveToFirst()) {
            Cart cart = new Cart();
            cart.setId(cursor.getLong(0));
            cart.setQuantity(cursor.getInt(3));
            int isSelectedInt = cursor.getInt(4); // Assuming is_selected field is stored as an integer (0 or 1)
            boolean isChosen = isSelectedInt == 1;
            cart.setChosen(isChosen);
            Product product = productDatabase.findById(productId);
            cart.setProduct(product);
            User user = userDataSource.getUserById(userId);
            cart.setUser(user);
            return cart;
        }
        return null;
    }

    public long addToCart (Long productId, int userId, UserDataSource userDataSource, ProductDatabase productDatabase) {
        long id = -1;
        Cart cart = findByProductAndUser(productId, userId, userDataSource, productDatabase );
        if ( cart != null) {
            updateCartQuantity(cart.getId(), cart.getQuantity() + 1);
            return cart.getId();
        }
            try {
                ContentValues values = new ContentValues();
                values.put("user_id", userId);
                values.put("product_id", productId);
                values.put("quantity", 1);
                values.put("is_selected", false);
                id = db.insert("Cart", null, values);
            } catch (Exception e) {
                // Handle any exceptions
                e.printStackTrace();
            }
            return id;

    }

    public int updateCartQuantity (Long cartId, int quantity) {
        int rowsAffected = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("quantity", quantity);
            rowsAffected = db.update("Cart", values, "id=?", new String[]{String.valueOf(cartId)});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }


    public int updateSelectedCart(Long cartId, boolean isSelected) {
        int rowsAffected = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("is_selected", isSelected ? 1 : 0);
            rowsAffected = db.update("Cart", values, "id=?", new String[]{String.valueOf(cartId)});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }


    public int remove (Long cartId) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("Cart", "id=?", new String[]{String.valueOf(cartId)});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public int removeByUserId (Integer userId) {
        int rowsAffected = -1;
        try {
            rowsAffected = db.delete("Cart", "user_id=?", new String[]{String.valueOf(userId)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }


    // Todo: Update selection by shop id


    public int updateSelectedCartByUser(int userId, boolean isSelected) {
        int rowsAffected = -1;
        try {
            ContentValues values = new ContentValues();
            values.put("is_selected", isSelected);
            rowsAffected = db.update("Cart", values, "user_id=?", new String[]{String.valueOf(userId)});
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
        }
        return rowsAffected;
    }

}
