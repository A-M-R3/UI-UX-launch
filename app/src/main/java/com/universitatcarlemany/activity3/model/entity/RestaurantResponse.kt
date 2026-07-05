package com.universitatcarlemany.activity3.model.entity

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(
    @SerializedName("restaurants")
    val restaurants: List<Restaurant>? = null,

    @SerializedName("data")
    val data: List<Restaurant>? = null
) {
    fun getList(): List<Restaurant> {
        return restaurants ?: data ?: emptyList()
    }
}