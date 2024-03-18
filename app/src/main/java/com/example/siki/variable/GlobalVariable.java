package com.example.siki.variable;

import android.app.Application;

import com.example.siki.model.User;

public class GlobalVariable extends Application {
    private boolean isLoggedIn = false;
    private User authUser = null;

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
}
