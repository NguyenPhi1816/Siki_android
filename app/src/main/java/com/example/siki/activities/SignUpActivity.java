package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.R;
import com.example.siki.database.AccountDataSource;
import com.example.siki.database.UserDataSource;
import com.example.siki.enums.AccountStatus;
import com.example.siki.enums.Role;
import com.example.siki.model.Account;
import com.example.siki.model.User;

import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText addressEditText;
    private EditText phoneNumberEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private RadioGroup genderRadioGroup;
    private EditText dateOfBirthEditText;
    private Button showDatePickerButton;
    private Button signUpButton;
    private Button signUpToLoginButton;
    private Button signUpToMain;
    private TextView firstNameErrorMessage;
    private TextView lastNameErrorMessage;
    private TextView addressErrorMessage;
    private TextView phoneNumberErrorMessage;
    private TextView emailErrorMessage;
    private TextView passwordErrorMessage;
    private TextView confirmPasswordErrorMessage;
    private TextView dateOfBirthErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firstNameEditText = findViewById(R.id.first_name);
        lastNameEditText = findViewById(R.id.last_name);
        addressEditText = findViewById(R.id.address);
        phoneNumberEditText = findViewById(R.id.phone_number);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        dateOfBirthEditText = findViewById(R.id.date_of_birth);
        showDatePickerButton = findViewById(R.id.show_date_picker_btn);
        signUpButton = findViewById(R.id.sign_up_btn);
        signUpToLoginButton = findViewById(R.id.sign_up_to_login_btn);
        signUpToMain = findViewById(R.id.sign_up_to_main);
        firstNameErrorMessage = findViewById(R.id.first_name_error_message);
        lastNameErrorMessage = findViewById(R.id.last_name_error_message);
        addressErrorMessage = findViewById(R.id.address_error_message);
        phoneNumberErrorMessage = findViewById(R.id.phone_number_error_message);
        emailErrorMessage = findViewById(R.id.email_error_message);
        passwordErrorMessage = findViewById(R.id.password_error_message);
        confirmPasswordErrorMessage = findViewById(R.id.confirm_password_error_message);
        dateOfBirthErrorMessage = findViewById(R.id.date_of_birth_error_message);

        firstNameErrorMessage.setVisibility(View.GONE);
        lastNameErrorMessage.setVisibility(View.GONE);
        addressErrorMessage.setVisibility(View.GONE);
        phoneNumberErrorMessage.setVisibility(View.GONE);
        emailErrorMessage.setVisibility(View.GONE);
        passwordErrorMessage.setVisibility(View.GONE);
        confirmPasswordErrorMessage.setVisibility(View.GONE);
        dateOfBirthErrorMessage.setVisibility(View.GONE);

        signUpToMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(SignUpActivity.this, MainActivity.class);
                SignUpActivity.this.startActivity(activityChangeIntent);
            }
        });

        firstNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editTextValidator(hasFocus, firstNameErrorMessage, firstNameEditText.getText().toString().isEmpty(), "Vui lòng nhập thông tin này");
            }
        });

        lastNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editTextValidator(hasFocus, lastNameErrorMessage, lastNameEditText.getText().toString().isEmpty(), "Vui lòng nhập thông tin này");
            }
        });

        addressEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editTextValidator(hasFocus, addressErrorMessage, addressEditText.getText().toString().isEmpty(), "Vui lòng nhập thông tin này");
            }
        });

        phoneNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String phoneNumberStr = phoneNumberEditText.getText().toString();
                final boolean condition = phoneNumberStr.length() != 10 || !phoneNumberStr.startsWith("0");
                        editTextValidator(hasFocus, phoneNumberErrorMessage, condition, "Số điện thoại không đúng");
            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String email = emailEditText.getText().toString();
                final boolean condition = email.isEmpty() || !isValidEmail(email);
                editTextValidator(hasFocus, emailErrorMessage, condition, "Địa chỉ email không đúng");
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editTextValidator(hasFocus, passwordErrorMessage, passwordEditText.getText().toString().isEmpty(), "Vui lòng nhập mật khẩu");
            }
        });

        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                editTextValidator(hasFocus, confirmPasswordErrorMessage, confirmPasswordEditText.getText().toString().isEmpty(), "Vui lòng nhập lại mật khẩu");
            }
        });

        dateOfBirthEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String dateOfBirth = dateOfBirthEditText.getText().toString();
                final boolean condition = dateOfBirth.isEmpty() || !isValidDate(dateOfBirth);
                editTextValidator(hasFocus, dateOfBirthErrorMessage, condition, "Vui lòng nhập một ngày (dd/MM/yyyy)");
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        signUpToLoginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activityChangeIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                SignUpActivity.this.startActivity(activityChangeIntent);
            }
        });
        showDatePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateOfBirth();
            }
        });
    }

    private void editTextValidator (boolean hasFocus, TextView tw, boolean condition, String message) {
        if (!hasFocus) {
            if (condition) {
                tw.setText(message);
                tw.setVisibility(View.VISIBLE);
            }
        }
        if (hasFocus) {
            tw.setText("");
            tw.setVisibility(View.GONE);
        }
    }

    private void setDateOfBirth() {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(SignUpActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        dateOfBirthEditText.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }
    private void signUp() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String phoneNumber = phoneNumberEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String dateOfBirth = dateOfBirthEditText.getText().toString();

        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
        String selectedGender = selectedGenderRadioButton.getText().toString();

        boolean isValidInfor = true;
        isValidInfor = !firstName.isEmpty();
        isValidInfor = !lastName.isEmpty();
        isValidInfor = !address.isEmpty();
        isValidInfor = phoneNumber.length() == 10 && phoneNumber.startsWith("0");
        isValidInfor = !email.isEmpty() && isValidEmail(email);
        isValidInfor = !password.isEmpty();
        isValidInfor = !confirmPassword.isEmpty();
        isValidInfor = !dateOfBirth.isEmpty() && isValidDate(dateOfBirth);

        if(isValidInfor) {
            if (password.equals(confirmPassword)){
                System.out.println("Hello world");
                final User retreivedUser = insertUserToDB(firstName, lastName, address, phoneNumber, selectedGender, dateOfBirth, email);
                if(retreivedUser != null) {
                    final Account retreivedAccount = insertAccountToDB(phoneNumber, password);
                    if(retreivedAccount != null) {
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Có lỗi xảy ra trong quá trình đăng ký tài khoản", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Có lỗi xảy ra trong quá trình đăng ký thông tin người dùng", Toast.LENGTH_SHORT).show();
                }
            } else {
                confirmPasswordErrorMessage.setText("Mật khẩu nhập lại chưa chính xác.");
            }
        } else {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
        }
    };

    public User insertUserToDB (String firstName,
                                String lastName,
                                String address,
                                String phoneNumber,
                                String gender,
                                String dateOfBirth,
                                String email) {
        Context context = this; // Provide your Android context here

        // Create a new user
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setAddress(address);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setGender(gender);
        newUser.setDateOfBirth(dateOfBirth);
        newUser.setAvatar(null);
        newUser.setEmail(email);

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

    public Account insertAccountToDB (String phoneNumber, String password) {
        Context context = this; // Provide your Android context here

        final String hashedPassword = hashPassword(password);


        // Create a new Account
        Account newAccount = new Account();
        newAccount.setPhoneNumber(phoneNumber);
        newAccount.setPassword(hashedPassword);
        newAccount.setUserRoleId(Role.USER.ordinal());
        newAccount.setStatus(String.valueOf(AccountStatus.ACTIVE));

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

    // Method to hash a password
    public static String hashPassword(String plainTextPassword) {
        // Generate a salt for hashing
        String salt = BCrypt.gensalt();

        // Hash the password using the generated salt
        String hashedPassword = BCrypt.hashpw(plainTextPassword, salt);

        return hashedPassword;
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false); // Không linh hoạt, phải khớp chính xác với định dạng
        try {
            Date date = sdf.parse(dateString);
            // Kiểm tra xem ngày được parse có giống với chuỗi đầu vào không
            return dateString.equals(sdf.format(date));
        } catch (ParseException e) {
            // Nếu parse thất bại, chuỗi không phù hợp định dạng
            return false;
        }
    }
}