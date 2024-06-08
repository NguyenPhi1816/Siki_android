package com.example.siki.API;

import com.example.siki.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/v1/home/test")
    Call<User> getUser();
}
