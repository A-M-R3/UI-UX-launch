package com.universitatcarlemany.activity3.model.entity

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import java.time.LocalDate
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class User(
    val name: String,
    val surname: String,
    val birthDate: LocalDate,
    val address: String,
    val phoneNumber: String,
    val email: String,
    val image: String
) : Parcelable, Serializable {

    @IgnoredOnParcel
    val orders = mutableListOf<Order>()

    @IgnoredOnParcel
    var inProgressOrder: Order = Order(this)

    @IgnoredOnParcel
    val paidOrders = mutableSetOf<Order>()

    @IgnoredOnParcel
    val deliveredOrders = mutableSetOf<Order>()

    init {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw IllegalArgumentException("Invalid phone number format")
        }
        if (!isValidEmail(email)) {
            throw IllegalArgumentException("Invalid email format")
        }
        orders.add(inProgressOrder)
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("(\\+376\\s?)?\\d{3}\\s?\\d{3}"))
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
    }

    fun addOrder(order: Order) {
        orders.add(order)

        when (order.status) {
            OrderStatus.IN_PROGRESS -> {
                orders.remove(inProgressOrder)
                inProgressOrder = order
            }
            OrderStatus.PAID -> paidOrders.add(order)
            OrderStatus.DELIVERED -> deliveredOrders.add(order)
        }
    }

    fun getAllOrders(): List<Order> = orders.toList()
}