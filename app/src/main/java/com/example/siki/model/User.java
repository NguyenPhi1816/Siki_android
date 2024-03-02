package com.example.siki.model;

public class User {
    private Long id;
    private String email;
    private String sdt;

    public User() {
    }

    public User(Long id, String email, String sdt) {
        this.id = id;
        this.email = email;
        this.sdt = sdt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", sdt='" + sdt + '\'' +
                '}';
    }
}
