package com.example.siki.API;

import com.example.siki.dto.category.CategoryDto;
import com.example.siki.dto.user.UserProfile;
import com.example.siki.model.User;
import com.example.siki.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserApi {
    Gson gson = new GsonBuilder().create();

    UserApi userApi = new Retrofit.Builder()
            .baseUrl(Constants.ApiUrlLocal.USER_SERVICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().
            create(UserApi.class);

    @GET("storefront/customer/profile")
    Call<UserProfile> getUserProfile(@Header("Authorization") String authHeader);
    @POST("storefront/signup")
    Call<UserProfile> signUp(@Body User user);

}
