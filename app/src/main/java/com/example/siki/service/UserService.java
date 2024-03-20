package com.example.siki.service;

import android.content.Context;

import com.example.siki.database.AccountDataSource;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Account;
import com.example.siki.model.User;

public class UserService {
    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public User insertUserToDB (User newUser) {
        // Initialize UserDataSource
        UserDataSource userDataSource = new UserDataSource(context);
        userDataSource.open();

        // Insert the new user into the database
        long userId = userDataSource.insertUser(newUser);
        System.out.println("New user ID: " + userId);

        // Retrieve the user from the database using the ID
        User retrievedUser = userDataSource.getUserById((int) userId);
        if (retrievedUser != null) {
            System.out.println("Retrieved user: " + retrievedUser.toString());
        } else {
            System.out.println("User not found");
        }

        // Close the database connection
        userDataSource.close();
        return retrievedUser;
    }

    public User getUserByPhoneNumber (String phoneNumber) {
        UserDataSource userDataSource = new UserDataSource(context);
        userDataSource.open();
        User retrievedUser = userDataSource.getUserPhoneNumber(phoneNumber);
        userDataSource.close();
        return retrievedUser;
    }
}
