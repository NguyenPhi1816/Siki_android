package com.example.siki.variable;

import android.app.Application;

import com.example.siki.model.User;

public class GlobalVariable extends Application {
    private boolean isLoggedIn = false;
    private User authUser = null;

    private String access_token ;

    public GlobalVariable(boolean isLoggedIn, User authUser, String access_token) {
        this.isLoggedIn = isLoggedIn;
        this.authUser = authUser;
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean isLoggedIn){
        this.isLoggedIn = isLoggedIn;
    }

    public User getAuthUser() {
        return authUser;
    }

    public void setAuthUser(User authUser) {
        this.authUser = authUser;
    }

    public void setAuthenticationInfor(User authUser, boolean isLoggedIn){
        this.authUser = authUser;
        this.isLoggedIn = isLoggedIn;
    }

    public GlobalVariable() {
    }

    public void setAuthenticationInfor(User authUser, boolean isLoggedIn, String access_token){
        this.authUser = authUser;
        this.isLoggedIn = isLoggedIn;
        this.access_token = access_token;
    }
}
