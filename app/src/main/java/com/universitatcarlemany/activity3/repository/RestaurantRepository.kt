package com.universitatcarlemany.activity3.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.util.LocalTimeAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalTime

class RestaurantRepository {

    // 1. Inyectamos el adaptador de fechas para que no se rompa al leer el JSON
    private val gson = GsonBuilder()
        .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://carlemany.recursivity.io/api/")
        .addConverterFactory(GsonConverterFactory.create(gson)) // Añadimos el gson aquí
        .build()

    private val apiService = retrofit.create(RestaurantApiService::class.java)

    suspend fun getRestaurantsFromApi(): List<Restaurant> {
        return try {
            apiService.getRestaurants()
        } catch (e: Exception) {
            // 2. ESTA ES LA CLAVE: Si algo falla, lo imprimirá en rojo en el Logcat
            Log.e("RETROFIT_ERROR", "Fallo al conectar o parsear la API: ${e.message}", e)
            emptyList()
        }
    }
}