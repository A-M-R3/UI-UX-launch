package com.universitatcarlemany.activity3.model.entity

data class OrderDTO(
    val id: Int,
    val status: String,
    val restaurantId: Int,
    val items: List<String>,
    val totalCost: Double,
    val paidDate: String,
    val deliveredDate: String?
)