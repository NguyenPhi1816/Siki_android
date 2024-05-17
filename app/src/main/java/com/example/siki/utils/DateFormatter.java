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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime formatStringToLocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parse the string to a LocalDateTime object
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);
        return localDateTime;
    }
}
