package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.siki.R;
import com.example.siki.database.AccountDataSource;
import com.example.siki.database.SikiDatabaseHelper;
import com.example.siki.database.UserDataSource;
import com.example.siki.model.Account;
import com.example.siki.model.User;

public class LoginActivity extends AppCompatActivity {

    public User insertUserToDB () {
        Context context = this; // Provide your Android context here

        // Create a new user
        User newUser = new User();
        newUser.setFirstName("John");
        newUser.setLastName("Doe");
        newUser.setAddress("123 Main St");
        newUser.setPhoneNumber("1234567890");
        newUser.setGender("Male");
        newUser.setDateOfBirth("1990-01-01");
        newUser.setAvatar("avatar.jpg");
        newUser.setEmail("john.doe@example.com");

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

    public Account insertAccountToDB () {
        Context context = this; // Provide your Android context here

        // Create a new Account
        Account newAccount = new Account();
        newAccount.setPhoneNumber("1234567890");
        newAccount.setPassword("password123");
        newAccount.setUserRoleId(1);
        newAccount.setStatus("active");

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

    public Account getAccountByCredentials (String phoneNumber, String password) {
        Context context = this;
        AccountDataSource accountDataSource = new AccountDataSource(context);
        accountDataSource.open();
        Account retrievedAccount = accountDataSource.getAccountByPhoneNumber(phoneNumber);
        if (retrievedAccount != null) {
            if (!password.equals(retrievedAccount.getPassword())) {
                accountDataSource.close();
                return null;
            }
            System.out.println("Retrieved Account: " + retrievedAccount.toString());
        } else {
            System.out.println("Account not found.");
        }
        accountDataSource.close();
        return retrievedAccount;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button LoginToHomeBtn = (Button) findViewById(R.id.login_to_home_btn);
        LoginToHomeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(activityChangeIntent);
            }
        });

        final Button CreateNewAccountBtn = (Button) findViewById(R.id.create_new_account_btn);
        CreateNewAccountBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(activityChangeIntent);
            }
        });

//        insertUserToDB();
//        insertAccountToDB();

        final Button SubmitButton = (Button) findViewById(R.id.submit_btn);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final EditText email = (EditText) findViewById(R.id.email);
                final EditText password = (EditText) findViewById(R.id.password);
                final TextView email_error_message = (TextView) findViewById(R.id.email_error_message);
                final TextView pass_error_message = (TextView) findViewById(R.id.pass_error_message);
                String email_str = email.getText().toString();
                String pass_str = password.getText().toString();

                Account account = getAccountByCredentials(email_str, pass_str);
                if (account == null) {
                    email_error_message.setText("Số điện thoại không đúng");
                    pass_error_message.setText("Mật khẩu không đúng");
                }
            }
        });
    }
}