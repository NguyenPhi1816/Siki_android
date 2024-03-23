package com.example.siki.utils;

import java.text.DecimalFormat;

public class PriceFormatter {
    public static String formatDouble(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.#");
        String formattedNumber = decimalFormat.format(number) + " ₫";
        return formattedNumber;
    }
}
