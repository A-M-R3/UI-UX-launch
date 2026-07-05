package com.universitatcarlemany.activity3.repository

import com.universitatcarlemany.activity3.model.entity.Restaurant
import retrofit2.http.GET

interface RestaurantApiService {
    @GET("restaurants")
    suspend fun getRestaurants(): List<Restaurant>
}