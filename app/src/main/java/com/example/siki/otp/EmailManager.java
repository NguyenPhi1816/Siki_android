package com.example.siki.otp;

import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager extends AsyncTask<String, Void, Void> {
    final static private String senderEmail = "n20dccn126@student.ptithcm.edu.vn";
    final static private String senderPassword = "djcu bthy wktf sfly";

    public static void sendOTP(String recipientEmail, String otp) {
        // Mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server address
        props.put("mail.smtp.port", "587"); // Port for TLS/STARTTLS

        // Authenticator for sending emails
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        };

        // Create a session with the SMTP server
        Session session = Session.getInstance(props, authenticator);

        try {
            // Create a MIME message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your One-Time Password (OTP)");
            message.setText("Your OTP is: " + otp);

            // Send the email
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        String recipientEmail = params[0];
        String otp = params[1];
        sendOTP(recipientEmail, otp);
        return null;
    }
}
