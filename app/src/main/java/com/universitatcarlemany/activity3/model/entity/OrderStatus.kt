package com.universitatcarlemany.activity3.model.entity

import android.graphics.Color
import android.widget.TextView

enum class OrderStatus {
    IN_PROGRESS,
    PAID,
    DELIVERED
}

fun OrderStatus.toSpanish(): String {
    return when (this) {
        OrderStatus.IN_PROGRESS -> "En progreso"
        OrderStatus.PAID -> "Pagado"
        OrderStatus.DELIVERED -> "Entregado"
    }
}

fun TextView.setStatusTextColor(status: OrderStatus) {
    when (status) {
        OrderStatus.IN_PROGRESS -> this.setTextColor(Color.GREEN)
        OrderStatus.PAID, OrderStatus.DELIVERED -> this.setTextColor(Color.BLACK)
    }
}