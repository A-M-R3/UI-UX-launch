package com.universitatcarlemany.activity3.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.util.LocalTimeAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalTime

class RestaurantRepository {

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://carlemany.recursivity.io/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val apiService = retrofit.create(RestaurantApiService::class.java)

    suspend fun getRestaurantsFromApi(): List<Restaurant> {
        return try {
            val response = apiService.getRestaurants()
            response.getList()
        } catch (e: Exception) {
            Log.e("RETROFIT_ERROR", "Error restaurantes: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun getMenuFromApi(restaurantId: Int): List<MenuItem> {
        return try {
            apiService.getMenu(restaurantId)
        } catch (e: Exception) {
            Log.e("RETROFIT_ERROR", "Error menu: ${e.message}", e)
            emptyList()
        }
    }
}