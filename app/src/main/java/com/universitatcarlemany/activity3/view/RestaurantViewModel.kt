package com.universitatcarlemany.activity3.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.universitatcarlemany.activity3.model.entity.Menu
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.model.entity.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class RestaurantViewModel() : ViewModel() {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants

    private val _restaurantsLoaded = MutableLiveData<Boolean>()
    val restaurantsLoaded: LiveData<Boolean> get() = _restaurantsLoaded

    private fun loadRestaurants(idRestaurant: Int? = null) {
        // [COMMENT] Fetch restaurants from the repository
    }

}
