package com.example.siki.service;

import android.content.Context;

import com.example.siki.database.AccountDataSource;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Account;
import com.example.siki.model.User;

public class AccountService {
    private Context context;

    public AccountService(Context context) {
        this.context = context;
    }

    public Account insertAccountToDB (Account newAccount) {
        // Initialize AccountDataSource
        AccountDataSource accountDataSource = new AccountDataSource(context);
        accountDataSource.open();

        // Insert the new account into the database
        long insertedId = accountDataSource.insertAccount(newAccount);
        System.out.println(insertedId);
        if (insertedId != -1) {
            System.out.println("Account inserted successfully with ID: " + insertedId);

            // Retrieve the account from the database using the phone number
            String phoneNumberToRetrieve = newAccount.getPhoneNumber();
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

    public Account getAccountByPhoneNumber (String phoneNumber) {
        AccountDataSource accountDataSource = new AccountDataSource(context);
        accountDataSource.open();
        Account retrievedAccount = accountDataSource.getAccountByPhoneNumber(phoneNumber);
        accountDataSource.close();
        return retrievedAccount;
    }

    public void updateAccount (Account account) {
        AccountDataSource accountDataSource = new AccountDataSource(context);
        accountDataSource.open();
        accountDataSource.updateAccount(account);
        accountDataSource.close();
    }
}
