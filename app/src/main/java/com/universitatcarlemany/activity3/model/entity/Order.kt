package com.universitatcarlemany.activity3.model.entity

import java.io.Serializable
import java.util.Date

data class Order(
    val user: User,
    var id: Int = 0,
    var restaurant: Restaurant? = null,
    var status: OrderStatus = OrderStatus.IN_PROGRESS,
    var date: Date = Date(),
    val items: MutableList<MenuItem> = mutableListOf()
) : Serializable {

    val totalCost: Double
        get() = items.sumOf { it.price }

    fun addItem(item: MenuItem) {
        items.add(item)
    }

    fun removeItem(item: MenuItem) {
        items.remove(item)
    }
}