package com.example.siki.utils;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatLocalDateTimeToString(LocalDateTime time) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = time.format(outputFormatter);
        return formattedDateTime;
    }
}
