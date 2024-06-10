package com.example.siki.API;

import com.example.siki.dto.auth.LoginDto;
import com.example.siki.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {
    Gson gson = new GsonBuilder().create();
    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.MINUTES.SECONDS)
            .readTimeout(30, TimeUnit.MINUTES.SECONDS)
            .writeTimeout(30, TimeUnit.MINUTES.SECONDS)
            .build();

    AuthApi authApi = new Retrofit.Builder()
            .baseUrl(Constants.ApiUrlLocal.LOGIN_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build().
            create(AuthApi.class);



    @FormUrlEncoded
    @POST("realms/siki/protocol/openid-connect/token")
    Call<LoginDto> login(@Field("client_id") String client_id,
                                  @Field("grant_type") String grant_type,
                                  @Field("client_secret") String client_secret,
                                  @Field("username") String username,
                                  @Field("password") String password,
                                  @Field("scope") String scope);
}
