package com.example.siki.service;

import android.content.Context;

import com.example.siki.database.AccountDataSource;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Account;
import com.example.siki.model.User;

public class UserService {
    public boolean insertUserInforToDB(Context context, User user, Account account) {
        final User retreivedUser = insertUserToDB(context, user);
        if(retreivedUser != null) {
            final Account retreivedAccount = insertAccountToDB(context, account);
            return retreivedAccount != null;
        }
        return false;
    }

    public User insertUserToDB (Context context, User newUser) {
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

    public Account insertAccountToDB (Context context, Account newAccount) {
        // Initialize AccountDataSource
        AccountDataSource accountDataSource = new AccountDataSource(context);
        accountDataSource.open();

        // Insert the new account into the database
        long insertedId = accountDataSource.insertAccount(newAccount);
        if (insertedId != -1) {
            System.out.println("Account inserted successfully with ID: " + insertedId);

            // Retrieve the account from the database using the phone number
            String phoneNumberToRetrieve = "1234567890";
            Account retrievedAccount = accountDataSource.getAccountByPhoneNumber(phoneNumberToRetrieve);
            if (retrievedAccount != null) {
                System.out.println("Retrieved Account: " + retrievedAccount.toString());
            } else {
                System.out.println("Account not found.");
            }
            accountDataSource.close();
            return retrievedAccount;
        } else {
            System.out.println("Failed to insert account into the database.");
        }

        // Close the database connection
        accountDataSource.close();
        return null;
    }
}
