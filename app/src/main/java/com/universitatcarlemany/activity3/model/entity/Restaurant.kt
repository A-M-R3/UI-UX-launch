package com.universitatcarlemany.activity3.model.entity

import java.io.Serializable

data class Restaurant(
    val id: Int,
    val name: String,
    val address: String,
    val openingTime: String,
    val closingTime: String,
    val image: String
) : Serializable