package com.example.siki.API;

import com.example.siki.dto.product.ProductDto;
import com.example.siki.dto.user.UserProfile;
import com.example.siki.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductApi {
    Gson gson = new GsonBuilder().create();

    ProductApi productApi = new Retrofit.Builder()
            .baseUrl(Constants.ApiUrlLocal.PRODUCT_SERVICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().
            create(ProductApi.class);


    @GET("storefront/category/{categoryId}")
    Call<List<ProductDto>> getByCategoryId(@Path("categoryId") Integer categoryId);


}
