package com.universitatcarlemany.activity3.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Restaurant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel(restaurant: Restaurant) : ViewModel() {
    private val _items = MutableLiveData<List<MenuItem>>()
    val items: MutableLiveData<List<MenuItem>> get() = _items
    val restaurantOwner = restaurant

    init {
        loadMenu(restaurant.getId())
    }

    private fun loadMenu(idRestaurant: Int) {
        // [COMMENT] Fetch the menu items for the specified restaurant
    }
}