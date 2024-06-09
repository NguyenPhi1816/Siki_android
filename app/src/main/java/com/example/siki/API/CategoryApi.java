package com.example.siki.API;

import com.example.siki.API.retrofit.RetrofitClient;
import com.example.siki.dto.category.CategoryDto;
import com.example.siki.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface CategoryApi {

    Gson gson = new GsonBuilder().create();


    CategoryApi categoryApi = new Retrofit.Builder()
            .baseUrl(Constants.ApiUrlLocal.PRODUCT_SERVICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().
            create(CategoryApi.class);


    @GET("category/parents")
    Call<List<CategoryDto>> findAllParents();

}
