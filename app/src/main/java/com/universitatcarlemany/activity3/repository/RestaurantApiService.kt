package com.universitatcarlemany.activity3.repository

import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.RestaurantResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantApiService {
    @GET("restaurants")
    suspend fun getRestaurants(): RestaurantResponse

    @GET("restaurants/{id}/menu")
    suspend fun getMenu(@Path("id") id: Int): List<MenuItem>
}