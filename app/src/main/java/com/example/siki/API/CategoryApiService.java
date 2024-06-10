package com.example.siki.API;

import com.example.siki.API.dto.CategoryDto;
import com.example.siki.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryApiService {

    @GET("category/all")
    Call<List<Category>> getListCategory();

    @POST("backoffice/category")
    Call<Void> saveCategory(@Body CategoryDto categoryDto);

    @PUT("backoffice/category/{id}")
    Call<Void> updateCategory(@Path("id") int id, @Body CategoryDto categoryDto);

    @DELETE("backoffice/category/{id}")
    Call<Void> deleteCategory(@Path("id") int id);
}
