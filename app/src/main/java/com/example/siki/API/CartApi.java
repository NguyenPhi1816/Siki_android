package com.example.siki.API;

import com.example.siki.dto.cart.CartDto;
import com.example.siki.dto.user.UserProfile;
import com.example.siki.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CartApi {

    Gson gson = new GsonBuilder().create();

    CartApi cartApi = new Retrofit.Builder()
            .baseUrl(Constants.ApiUrlLocal.CART_SERVICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().
            create(CartApi.class);

    @POST("storefront/add-to-cart/{productId}")
    Call<Void> addToCart(@Path("productId") Long productId, @Header("Authorization") String token);

    @GET("storefront/{customerId}")
    Call<List<CartDto>> getCartByUserId(@Path("customerId") String customerId, @Header("Authorization") String token);

    @PUT("storefront/{cartId}/quantity/{quantity}")
    Call<Void> updateCartQuantity(@Path("cartId") Long cartId, @Path("quantity") int quantity,@Header("Authorization") String token);

    @PUT("storefront/{cartId}/selection/{selection}")
    Call<Void> updateCartSelection(@Path("cartId") Long cartId, @Path("selection") boolean selection, @Header("Authorization") String token);

    @PUT("storefront/selection/{selection}")
    Call<Void> updateCartSelectionByUser(@Path("selection") boolean selection, @Header("Authorization") String token);

    @DELETE("storefront")
    Call<Void> deleteAllCart(@Header("Authorization") String token);

    @DELETE("storefront/{cartId}")
    Call<Void> deleteCartById(@Path("cartId") Long cartId, @Header("Authorization") String token);

}
