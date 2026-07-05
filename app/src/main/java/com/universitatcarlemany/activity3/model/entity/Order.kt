package com.universitatcarlemany.activity3.model.entity

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Order(
    private val user: User,
    private var items: MutableList<MenuItem> = mutableListOf(),
    private var status: OrderStatus = OrderStatus.IN_PROGRESS,
    private var restaurant: Restaurant? = null,
    private var paidDate: LocalDateTime? = null,
    private var deliveredDate: LocalDateTime? = null
) {
    private var id: Int = nextId++
    private var totalCost = 0.0

    companion object {
        private var nextId = 1
    }

    fun setId(orderId: Int) {
        if (orderId <= 0) {
            throw IllegalArgumentException("Order ID must be a positive number")
        }
        this.id = orderId
    }

    fun getId(): Int = id

    fun addItem(item: MenuItem) {
        if (status != OrderStatus.IN_PROGRESS) {
            throw IllegalStateException("Order is not in progress")
        }

        if (getRestaurant() == null) {
            setRestaurant(item.getRestaurant())
        } else if (restaurant != item.getRestaurant()) {
            throw IllegalArgumentException("Items must belong to the same restaurant")
        }

        items.add(item)
        totalCost += item.getPrice()
    }

    fun removeItem(item: MenuItem) {
        items.remove(item)
        totalCost -= item.getPrice()

        if (items.isEmpty()) {
            setRestaurant(null)
        }
    }

    fun getTotalCost(): Double = totalCost

    fun getUser(): User = user

    fun getItems(): List<MenuItem> = items

    fun getStatus(): OrderStatus = status

    fun setStatus(newStatus: OrderStatus) {
        status = newStatus
    }

    fun getRestaurant(): Restaurant? = restaurant

    private fun setRestaurant(restaurant: Restaurant?) {
        this.restaurant = restaurant
    }

    fun getPaidDate(): LocalDateTime? = paidDate

    fun setPaidDate() {
        paidDate = LocalDateTime.now()
    }

    fun getDeliveredDate(): LocalDateTime? = deliveredDate

    fun toDto() : OrderDTO {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        return OrderDTO(
            id = getId(),
            status = getStatus().toString(),
            restaurantId = getRestaurant()?.getId() ?: -1,
            items = getItems().map { it.getId() },
            totalCost = getTotalCost(),
            paidDate = getPaidDate()?.format(formatter) ?: "",
            deliveredDate = getDeliveredDate()?.format(formatter)
        )
    }
}