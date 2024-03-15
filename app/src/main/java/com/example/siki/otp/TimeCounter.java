package com.example.siki.otp;

import android.os.Handler;
import android.widget.TextView;

import android.os.CountDownTimer;
import android.widget.TextView;

public class TimeCounter {
    private CountDownTimer countDownTimer;
    private TextView textView;
    private long timeLeftInMillis;
    private boolean isRunning;

    public TimeCounter(TextView textView) {
        this.textView = textView;
        this.isRunning = false;
    }

    public void start() {
        if (isRunning) {
            stop();
        }
        timeLeftInMillis = 120000; // 120 seconds
        isRunning = true;

        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                isRunning = false;
                updateCountdownText();
            }
        }.start();
    }

    public void stop() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isRunning = false;
        }
    }

    public void reset() {
        stop();
        timeLeftInMillis = 120000;
        updateCountdownText();
    }

    private void updateCountdownText() {
        int seconds = (int) (timeLeftInMillis / 1000);
        int minutes = seconds / 60;
        seconds %= 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        textView.setText(timeLeftFormatted);
    }
}


