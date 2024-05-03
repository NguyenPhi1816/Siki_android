package com.example.siki.model;

import java.io.Serializable;

public class Account implements Serializable {
    private String phoneNumber;
    private String password;
    private String role;
    private String status;

    public Account() {
        // Default constructor
    }

    // Constructor with parameters
    public Account(String phoneNumber, String password, int userRoleId, String status) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    // Getters and setters for each field

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Account{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status='" + status + '\'' +
                '}';
    }
}
