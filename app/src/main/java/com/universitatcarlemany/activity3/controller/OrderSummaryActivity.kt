package com.universitatcarlemany.activity3.controller

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.OrderUserDetailsAdapter
import com.universitatcarlemany.activity3.model.entity.OrderDTO
import com.universitatcarlemany.activity3.model.entity.OrderStatus
import com.universitatcarlemany.activity3.model.entity.User
import com.universitatcarlemany.activity3.repository.RestaurantRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrderSummaryActivity : ComponentActivity() {

    private val repository = RestaurantRepository()

    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Resumen del Pedido"

        val user: User? = intent.getParcelableExtra("user", User::class.java)

        if (user == null) {
            finish()
            return
        }

        val order = OrderManager.getOrder(user)

        if (order != null) {
            val recyclerView = findViewById<RecyclerView>(R.id.order_items_recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(this)

            val adapter = OrderUserDetailsAdapter(this, order.items, order)
            recyclerView.adapter = adapter

            val totalTextView = findViewById<TextView>(R.id.order_total_text)
            totalTextView.text = "Total: €${String.format("%.2f", order.totalCost)}"

            val finalizeOrderButton: Button = findViewById(R.id.finalize_order_button)

            finalizeOrderButton.setOnClickListener {

                // 1. SOLUCIÓN BLINDADA: Delegamos al Manager que ya compila en tu PC para fijar el estado y la fecha
                OrderManager.saverOrder(order)

                finalizeOrderButton.isEnabled = false
                finalizeOrderButton.text = "Procesando pago..."

                lifecycleScope.launch {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

                    // 2. PARCHE DE TIPO: Forzamos toString() antes de toInt() para que nunca colapse
                    val restId = order.restaurant?.id?.toString()?.toIntOrNull() ?: -1

                    val pedidoOptimizado = OrderDTO(
                        id = order.id,
                        status = order.status.toString(),
                        restaurantId = restId,
                        items = order.items.map { it.id.toString() },
                        totalCost = order.totalCost,
                        paidDate = LocalDateTime.now().format(formatter),
                        deliveredDate = null
                    )

                    val respuestaServidor = repository.createOrderInApi(user.email, pedidoOptimizado)

                    if (respuestaServidor != null) {
                        Toast.makeText(this@OrderSummaryActivity, "¡Pedido finalizado con éxito!", Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        order.status = OrderStatus.IN_PROGRESS
                        finalizeOrderButton.isEnabled = true
                        finalizeOrderButton.text = "Finalizar pedido"
                        Toast.makeText(this@OrderSummaryActivity, "Error de conexión al procesar", Toast.LENGTH_LONG).show()
                    }
                }
            }

            if (order.items.isEmpty()) {
                finalizeOrderButton.visibility = View.GONE
                Toast.makeText(this, "Pedido sin artículos, agregue platos", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                finalizeOrderButton.visibility = View.VISIBLE
            }

            val backButton: Button = findViewById(R.id.back_button)
            backButton.setOnClickListener {
                finish()
            }
        } else {
            Toast.makeText(this, "No hay ningún pedido en progreso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun reload() {
        finish()
        startActivity(intent)
    }
}