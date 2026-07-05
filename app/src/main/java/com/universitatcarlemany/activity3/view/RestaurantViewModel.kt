package com.universitatcarlemany.activity3.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.repository.RestaurantRepository
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {
    
    private val repository = RestaurantRepository()
    
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    init {
        loadRestaurants()
    }

    private fun loadRestaurants() {
        viewModelScope.launch {
            val result = repository.getRestaurantsFromApi()
            _restaurants.postValue(result)
        }
    }
}