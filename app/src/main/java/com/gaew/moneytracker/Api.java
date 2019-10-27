package com.gaew.moneytracker;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("auth")

    Call<Status> auth(@Query("social_user_id") String userId);

    @GET("items")  // аннотация ГЕТ, потому что это запрос ГЕТ
    Call<List<Item>> getItems(@Query("type") String type, @Query("auth-token") String token);


    @POST("items/add")
    Call< Status> addItem(@Body AddItemRequest request, @Query("auth-token") String token);

    @POST("items/remove")
    Call<Status> removeItem(@Query("id") String id, @Query("auth-token") String token);
}
