package com.universitatcarlemany.activity3.repository

import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.OrderDTO
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.model.entity.RestaurantResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestaurantApiService {
    @GET("restaurants")
    suspend fun getRestaurants(): RestaurantResponse

    @GET("restaurants/{id}/menu")
    suspend fun getRestaurantMenu(@Path("id") restaurantId: String): List<MenuItem>

    @GET("user/{email}/orders")
    suspend fun getUserOrders(@Path("email") userEmail: String): List<OrderDTO>

    @POST("user/{email}/orders")
    suspend fun createOrder(@Path("email") userEmail: String, @Body order: OrderDTO): OrderDTO

    @PUT("user/{email}/orders/{orderId}")
    suspend fun updateOrder(
        @Path("email") userEmail: String,
        @Path("orderId") orderId: Int,
        @Body order: OrderDTO
    ): OrderDTO
}