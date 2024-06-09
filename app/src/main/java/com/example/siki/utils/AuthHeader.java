package com.example.siki.utils;

public class AuthHeader {
    public static String convertAuthHeaderFromAccessToken(String token) {
        String header = "Bearer " + token;
        return header;
    }
}
