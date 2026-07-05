package com.universitatcarlemany.activity3.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.repository.RestaurantRepository
import kotlinx.coroutines.launch

class MenuViewModel(restaurant: Restaurant) : ViewModel() {

    private val repository = RestaurantRepository()

    private val _items = MutableLiveData<List<MenuItem>>()
    val items: MutableLiveData<List<MenuItem>> get() = _items

    val restaurantOwner = restaurant

    init {
        loadMenu(restaurant.id)
    }

    private fun loadMenu(idRestaurant: Int) {
        // Lanzamos la corrutina para descargar los platos de este restaurante en específico
        viewModelScope.launch {
            _items.value = repository.getMenuFromApi(idRestaurant)
        }
    }
}