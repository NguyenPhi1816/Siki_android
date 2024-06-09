package com.example.siki.model;

import com.example.siki.dto.user.UserProfile;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("address")
    private String address;

    @SerializedName("phoneNumber")
    private String phoneNumber;

    @SerializedName("gender")
    private String gender;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("email")
    private String email;


    public User() {
    }

    public User(String id, String firstName, String lastName, String address, String phoneNumber, String gender, String dateOfBirth, String avatar, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.avatar = avatar;
        this.email = email;
    }

    public User(UserProfile userProfile) {
        this.id = userProfile.getId();
        this.firstName = userProfile.getFirstName();
        this.lastName = userProfile.getLastName();
        this.address = userProfile.getAddress();
        this.phoneNumber = userProfile.getPhoneNumber();
        this.gender = "Male";
        this.dateOfBirth = userProfile.getDateOfBirth();
        this.avatar = userProfile.getAvatar() == null ? "" : userProfile.getAvatar();
        this.email = userProfile.getEmail();
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return this.firstName + " " + this.lastName;
    }
}
