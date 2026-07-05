package com.universitatcarlemany.activity3.repository

import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Order
import com.universitatcarlemany.activity3.model.entity.Restaurant
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RestaurantApiService {

    @GET("restaurants")
    suspend fun getRestaurants(): List<Restaurant>

    @GET("restaurants/{id}/menu")
    suspend fun getRestaurantMenu(@Path("id") restaurantId: String): List<MenuItem>

    @GET("user/{email}/orders")
    suspend fun getUserOrders(@Path("email") userEmail: String): List<Order>

    @POST("user/{email}/orders")
    suspend fun createOrder(@Path("email") userEmail: String, @Body order: Order): Order
}