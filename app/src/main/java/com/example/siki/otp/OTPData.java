package com.example.siki.otp;

import java.time.LocalDateTime;

public class OTPData {
    private String otp;
    private LocalDateTime creationTime;
    private int attempts;

    public OTPData(String otp, LocalDateTime creationTime) {
        this.otp = otp;
        this.creationTime = creationTime;
        this.attempts = 0;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        attempts++;
    }
}