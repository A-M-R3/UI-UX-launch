package com.universitatcarlemany.activity3.repository

import com.universitatcarlemany.activity3.model.entity.Restaurant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestaurantRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.ejemplo.com/") 
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(RestaurantApiService::class.java)

    suspend fun getRestaurantsFromApi(): List<Restaurant> {
        return try {
            apiService.getRestaurants()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}