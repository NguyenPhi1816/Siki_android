package com.example.siki.model;

public class Account {
    private String phoneNumber;
    private String password;
    private int userRoleId;
    private String status;

    public Account() {
        // Default constructor
    }

    // Constructor with parameters
    public Account(String phoneNumber, String password, int userRoleId, String status) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRoleId = userRoleId;
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

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
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
                ", userRoleId=" + userRoleId +
                ", status='" + status + '\'' +
                '}';
    }
}
