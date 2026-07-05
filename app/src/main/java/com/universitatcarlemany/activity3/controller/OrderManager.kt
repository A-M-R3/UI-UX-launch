package com.universitatcarlemany.activity3.controller

import android.util.Log
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Order
import com.universitatcarlemany.activity3.model.entity.OrderStatus
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.model.entity.User

object OrderManager {
    private val orders = mutableListOf<Order>()

    fun createOrder(user: User, restaurant: Restaurant?): Order {
        val order = Order(
            user = user,
            restaurant = restaurant
        )
        orders.add(order)
        user.addOrder(order)
        return order
    }

    fun getOrder(user: User): Order? {
        return orders.find {
            it.getUser().getEmail() == user.getEmail() && it.getStatus() == OrderStatus.IN_PROGRESS
        }
    }

    fun addItemToOrder(order: Order, item: MenuItem) {
        order.addItem(item)
    }

    fun saverOrder(order: Order) {
        order.setStatus(OrderStatus.PAID)
        order.setPaidDate()

        // [COMMENT] Create order here and save it to the API

    }

    fun getOrders(): List<Order> {
        return orders
    }
}
