package com.universitatcarlemany.activity3.controller

import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Order
import com.universitatcarlemany.activity3.model.entity.OrderStatus
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.model.entity.User
import java.util.Date

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
            it.user.email == user.email && it.status == OrderStatus.IN_PROGRESS
        }
    }

    fun addItemToOrder(order: Order, item: MenuItem) {
        order.addItem(item)
    }

    fun saverOrder(order: Order) {
        order.status = OrderStatus.PAID
        order.date = Date()
    }

    fun getOrders(): List<Order> {
        return orders
    }
}