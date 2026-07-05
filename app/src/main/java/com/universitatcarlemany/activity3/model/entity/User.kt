package com.universitatcarlemany.activity3.model.entity

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import java.time.LocalDate
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    private val name: String,
    private val surname: String,
    private val birthDate: LocalDate,
    private val address: String,
    private val phoneNumber: String,
    private val email: String,
    private val image: String
) : Parcelable {
    @IgnoredOnParcel
    private val orders = mutableListOf<Order>()
    @IgnoredOnParcel
    private var inProgressOrder: Order = Order(this)
    @IgnoredOnParcel
    private val paidOrders = mutableSetOf<Order>()
    @IgnoredOnParcel
    private val deliveredOrders = mutableSetOf<Order>()

    init {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw IllegalArgumentException("Invalid phone number format")
        }
        if (!isValidEmail(email)) {
            throw IllegalArgumentException("Invalid email format")
        }

        orders.add(inProgressOrder)
    }
    fun getImage(): String = image

    fun getName(): String = name

    fun getBirthDate(): LocalDate = birthDate

    fun getPhoneNumber(): String = phoneNumber

    fun getEmail(): String = email

    // Andorra phone number validation
    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("(\\+376\\s?)?\\d{3}\\s?\\d{3}"))
    }

    // Email validation function
    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
    }

    fun addOrder(order: Order) {
        orders.add(order)

        when (order.getStatus()) {
            OrderStatus.IN_PROGRESS -> {
                // Remove the previous order if the user already has one in the database
                orders.remove(inProgressOrder)
                inProgressOrder = order
            }
            OrderStatus.PAID -> paidOrders.add(order)
            OrderStatus.DELIVERED -> deliveredOrders.add(order)
        }
    }

    fun getAllOrders(): List<Order> = orders.toList()
}