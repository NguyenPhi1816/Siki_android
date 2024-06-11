package com.example.siki.API;

import com.example.siki.API.dto.MediaDto;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MediaApiService {

    String BASE_URL = "http://192.168.1.8:8090/api/medias/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    MediaApiService apiService = retrofit.create(MediaApiService.class);

    @Multipart
    @POST("create")
    Call<MediaDto> createMedia( @Part MultipartBody.Part file, @Part("type") RequestBody type);
}
