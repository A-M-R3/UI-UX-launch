package com.universitatcarlemany.activity3.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Order
import com.universitatcarlemany.activity3.model.entity.OrderDTO
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.model.entity.RestaurantResponse
import com.universitatcarlemany.activity3.util.LocalDateAdapter
import com.universitatcarlemany.activity3.util.LocalTimeAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalTime

class RestaurantRepository {

    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
        .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://carlemany.recursivity.io/api/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val apiService = retrofit.create(RestaurantApiService::class.java)

    suspend fun getRestaurantsFromApi(): List<Restaurant> {
        return try {
            val response = apiService.getRestaurants()
            response.restaurants ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Cambiado a Int para acoplarse con MenuViewModel y fulminar el Type Mismatch
    suspend fun getMenuFromApi(restaurantId: Int): List<MenuItem> {
        return try {
            apiService.getRestaurantMenu(restaurantId.toString())
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getUserOrdersFromApi(email: String): List<Order> {
        return try {
            apiService.getUserOrders(email)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun createOrderInApi(email: String, orderDto: OrderDTO): OrderDTO? {
        return try {
            apiService.createOrder(email, orderDto)
        } catch (e: Exception) {
            Log.e("RETROFIT_ERROR", "Error al enviar el pedido por POST: ${e.message}", e)
            null
        }
    }
}