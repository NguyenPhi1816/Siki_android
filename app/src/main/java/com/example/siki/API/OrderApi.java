package com.example.siki.API;

import com.example.siki.dto.order.OrderDto;
import com.example.siki.dto.order.OrderPostDto;
import com.example.siki.dto.order.OrderStatusDto;
import com.example.siki.dto.product.ProductVariantDto;
import com.example.siki.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderApi {
    Gson gson = new GsonBuilder().create();

    OrderApi orderApi = new Retrofit.Builder()
            .baseUrl(Constants.ApiUrlLocal.ORDER_SERVICE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().
            create(OrderApi.class);
    @POST("storefront")
    Call<Long> createOrder(@Header("Authorization") String token, @Body OrderPostDto orderPostDto);

    @PUT("storefront/{orderId}")
    Call<OrderDto> getById(@Header("Authorization") String token,  @Path("orderId") Long orderId);

    @PUT("storefront/{orderId}/status/{orderStatus}")
    Call<OrderDto> updateStatusByOrderId(@Header("Authorization") String token,  @Path("orderId") Long orderId,  @Path("orderStatus") OrderStatusDto orderStatusDto);
    @GET("backoffice")
    Call<List<OrderDto>> getAll(@Header("Authorization") String token);

    @GET("backoffice/status/{orderStatus}")
    Call<List<OrderDto>> getAllByStatus(@Header("Authorization") String token, @Path("orderStatus") OrderStatusDto orderStatusDto);

    @GET("storefront")
    Call<List<OrderDto>> getOrderOfUser(@Header("Authorization") String token);


    @GET("storefront/status/{orderStatus}")
    Call<List<OrderDto>> getByUserAndStatus(@Header("Authorization") String token,
                                            @Path("orderStatus")OrderStatusDto orderStatusDto);
    @GET("storefront/{orderId}/status/{orderStatus}")
    Call<List<OrderDto>> updateOrderStatusByUser(@Header("Authorization") String token,
                                            @Path("orderId") Long orderId,
                                            @Path("orderStatus")OrderStatusDto orderStatusDto);



    @GET("10-beseller-products")
    Call<List<ProductVariantDto>> getTop10Products();



}
