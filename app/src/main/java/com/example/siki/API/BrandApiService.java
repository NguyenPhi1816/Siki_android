package com.example.siki.API;

import com.example.siki.API.dto.BrandDto;
import com.example.siki.API.dto.CategoryDto;
import com.example.siki.model.Brand;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BrandApiService {

    @GET("brand/all")
    Call<List<Brand>> getAllBrands();

    @PUT("backoffice/brand/{id}")
    Call<Void> updateBrand(@Path("id") int id, @Body Brand brand, @Header("Authorization") String token);

    @POST("backoffice/brand")
    Call<Void> saveBrand(@Body BrandDto brandDto,  @Header("Authorization") String token);

    @DELETE("backoffice/brand/{id}")
    Call<Void> deleteBrand(@Path("id") int id, @Header("Authorization") String token);
}
