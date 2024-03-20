package com.example.siki.FormValidator;

import android.view.View;
import android.widget.TextView;

public class FormValidator {
    public static void editTextValidator (boolean hasFocus, TextView tw, boolean condition, String message) {
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
}
