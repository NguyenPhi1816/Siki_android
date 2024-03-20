package com.example.siki.otp;

import android.os.AsyncTask;
import android.os.Build;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.*;
import javax.mail.internet.*;

public class OTPManager {
    private static final long OTP_EXPIRATION_MINUTES = 2; // OTP expiration time in minutes
    private static final int MAX_ATTEMPTS = 3; // Maximum number of OTP validation attempts

    private static Map<String, OTPData> otpDataMap = new HashMap<>();

    public static String generateOTP(String userId) {
        String otp = generateRandomOTP(); // You need to implement a method to generate the OTP
        OTPData otpData = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            otpData = new OTPData(otp, LocalDateTime.now());
        }
        otpDataMap.put(userId, otpData);
        return otp;
    }

    public static boolean validateOTP(String userId, String otp) {
        OTPData otpData = otpDataMap.get(userId);
        if (otpData == null || otpData.getAttempts() >= MAX_ATTEMPTS) {
            return false; // OTP not found or maximum attempts reached
        }

        // Check if OTP matches and it hasn't expired
        if (otp.equals(otpData.getOtp()) && !isOTPExpired(otpData.getCreationTime())) {
            otpDataMap.remove(userId); // OTP is valid, remove it from the map
            return true;
        } else {
            otpData.incrementAttempts(); // Increment the number of attempts
            return false;
        }
    }

    private static boolean isOTPExpired(LocalDateTime creationTime) {
        LocalDateTime currentTime = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentTime = LocalDateTime.now();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return creationTime.until(currentTime, ChronoUnit.MINUTES) >= OTP_EXPIRATION_MINUTES;
        }
        return false;
    }

    private static String generateRandomOTP() {
        // Length of the OTP code
        int length = 6;
        // Characters allowed in the OTP
        String characters = "0123456789";
        // Create a StringBuilder to store the OTP
        StringBuilder otp = new StringBuilder();
        // Create a Random object
        Random random = new Random();

        // Generate OTP code of specified length
        for (int i = 0; i < length; i++) {
            // Append a random character from the characters string
            otp.append(characters.charAt(random.nextInt(characters.length())));
        }

        // Convert StringBuilder to String and return the OTP
        return otp.toString();
    }
}
