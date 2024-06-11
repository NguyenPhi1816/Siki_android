package com.example.siki.API.retrofit;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.1.8:8090/api/products/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Interceptor interceptor = chain -> {
                Request request = chain.request();
                Request.Builder builder = request.newBuilder();
                return chain.proceed(builder.build());
            };

            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder().addInterceptor(interceptor);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okBuilder.build())
                    .build();
        }
        return retrofit;
    }
}
