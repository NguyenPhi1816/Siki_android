package com.example.siki.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siki.API.UserApi;
import com.example.siki.Bcrypt.BcryptManager;
import com.example.siki.FormValidator.FormValidator;
import com.example.siki.R;
import com.example.siki.database.AccountDataSource;
import com.example.siki.database.UserDataSource;
import com.example.siki.dto.user.UserProfile;
import com.example.siki.enums.AccountStatus;
import com.example.siki.enums.Role;
import com.example.siki.model.Account;
import com.example.siki.model.User;
import com.saadahmedev.popupdialog.PopupDialog;

import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);
    private static final String DATE_FORMAT = "yyyy-MM-dd";
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
//    private Button signUpToMain;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the icon for the back button (should be a left chevron)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_chevron_left);  // Replace with your own icon here
        }

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
//        signUpToMain = findViewById(R.id.sign_up_to_main);
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

//        signUpToMain.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent activityChangeIntent = new Intent(SignUpActivity.this, MainActivity.class);
//                SignUpActivity.this.startActivity(activityChangeIntent);
//            }
//        });

        firstNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                FormValidator.editTextValidator(hasFocus, firstNameErrorMessage, firstNameEditText.getText().toString().isEmpty(), "Vui lòng nhập thông tin này");
            }
        });

        lastNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                FormValidator.editTextValidator(hasFocus, lastNameErrorMessage, lastNameEditText.getText().toString().isEmpty(), "Vui lòng nhập thông tin này");
            }
        });

        addressEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                FormValidator.editTextValidator(hasFocus, addressErrorMessage, addressEditText.getText().toString().isEmpty(), "Vui lòng nhập thông tin này");
            }
        });

        phoneNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String phoneNumberStr = phoneNumberEditText.getText().toString();
                final boolean condition = phoneNumberStr.length() != 10 || !phoneNumberStr.startsWith("0");
                FormValidator.editTextValidator(hasFocus, phoneNumberErrorMessage, condition, "Số điện thoại không đúng");
            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String email = emailEditText.getText().toString();
                final boolean condition = email.isEmpty() || !isValidEmail(email);
                FormValidator.editTextValidator(hasFocus, emailErrorMessage, condition, "Địa chỉ email không đúng");
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                FormValidator.editTextValidator(hasFocus, passwordErrorMessage, passwordEditText.getText().toString().isEmpty(), "Vui lòng nhập mật khẩu");
            }
        });

        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                FormValidator.editTextValidator(hasFocus, confirmPasswordErrorMessage, confirmPasswordEditText.getText().toString().isEmpty(), "Vui lòng nhập lại mật khẩu");
            }
        });

        dateOfBirthEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final String dateOfBirth = dateOfBirthEditText.getText().toString();
                final boolean condition = dateOfBirth.isEmpty() || !isValidDate(dateOfBirth);
                FormValidator.editTextValidator(hasFocus, dateOfBirthErrorMessage, condition, "Vui lòng nhập một ngày (dd/MM/yyyy)");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to previous one if there is any
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                        String month = String.format("%02d", monthOfYear + 1);
                        String day = String.format("%02d", dayOfMonth);
                        // Display Selected date in textbox
                        dateOfBirthEditText.setText(year + "-"
                                + month + "-" + day);

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

        boolean isValidInfor = !firstName.isEmpty()
                                && !lastName.isEmpty()
                                && !address.isEmpty()
                                && phoneNumber.length() == 10 && phoneNumber.startsWith("0")
                                && !email.isEmpty() && isValidEmail(email)
                                && !password.isEmpty()
                                && !confirmPassword.isEmpty()
                                && !dateOfBirth.isEmpty() && isValidDate(dateOfBirth);

        if(isValidInfor) {
            if (password.equals(confirmPassword)){
                // Create a new user
                User newUser = new User();
                newUser.setFirstName(firstName);
                newUser.setLastName(lastName);
                newUser.setAddress(address);
                newUser.setPhoneNumber(phoneNumber);
                newUser.setGender(selectedGender);
                newUser.setDateOfBirth(dateOfBirth);
                newUser.setAvatar("");
                newUser.setEmail(email);
                newUser.setPassword(password);

                UserApi.userApi.signUp(newUser).enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                        System.out.println(response.body());
                        if (response.isSuccessful()) {
                            showSuccessMessage();

                        }else {
                            Toast.makeText(getApplicationContext(), response.message().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });


              /*  intent.putExtra("newUser", newUser);
                intent.putExtra("newAccount", newAccount);*/
            } else {
                confirmPasswordErrorMessage.setText("Mật khẩu nhập lại chưa chính xác.");
            }
        } else {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
        }
    };

    private void showSuccessMessage() {
        PopupDialog.getInstance(this)
                .statusDialogBuilder()
                .createSuccessDialog()
                .setHeading("Thành công")
                .setDescription("Bạn đã tạo tài khoản thành công")
                .setActionButtonText("Đăng nhập ngay")
                .build(dialog -> {
                    dialog.dismiss();
                    navigateToLoginPage();
                })
                .show();
    }

    private void navigateToLoginPage() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false); // Không linh hoạt, phải khớp chính xác với định dạng
        try {
            sdf.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}