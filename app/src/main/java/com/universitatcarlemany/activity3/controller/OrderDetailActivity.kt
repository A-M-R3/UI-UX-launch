package com.universitatcarlemany.activity3.controller

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.OrderDetailAdapter
import com.universitatcarlemany.activity3.model.entity.OrderDTO
import com.universitatcarlemany.activity3.model.entity.OrderStatus
import com.universitatcarlemany.activity3.model.entity.User
import com.universitatcarlemany.activity3.repository.RestaurantRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrderDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        val orderId = intent.getIntExtra("order_id", -1)
        val user: User? = intent.getParcelableExtra("user", User::class.java)

        if (user == null) {
            finish()
            return
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        val backButton: Button = findViewById(R.id.back_button)

        if (orderId != -1) {
            val order = OrderManager.getOrders().find {
                it.id == orderId && it.user.email == user.email
            }

            if (order != null) {
                toolbar.title = "PEDIDO #" + orderId.toString().uppercase()

                val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(this)

                val adapter = OrderDetailAdapter(order.items)
                recyclerView.adapter = adapter

                val parentLayout = backButton.parent as? LinearLayout
                if (parentLayout != null) {

                    val modifyButton = Button(this).apply {
                        text = "Marcar como Entregado (PUT)"
                        setOnClickListener {
                            lifecycleScope.launch {
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                                order.status = OrderStatus.DELIVERED
                                val repository = RestaurantRepository()

                                // CORRECCIÓN: Reutilizamos order.rawPaidDate para pasar el filtro del servidor
                                val dto = OrderDTO(
                                    id = order.id,
                                    status = "DELIVERED",
                                    restaurantId = order.restaurant?.id ?: -1,
                                    items = order.items.map { it.id },
                                    totalCost = order.totalCost,
                                    paidDate = order.rawPaidDate,
                                    deliveredDate = LocalDateTime.now().format(formatter)
                                )
                                val res = repository.updateOrderInApi(user.email.lowercase().trim(), order.id, dto)
                                if (res != null) {
                                    Toast.makeText(this@OrderDetailActivity, "¡Pedido modificado a ENTREGADO!", Toast.LENGTH_LONG).show()
                                    finish()
                                } else {
                                    Toast.makeText(this@OrderDetailActivity, "Error al actualizar", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    val cancelButton = Button(this).apply {
                        text = "Cancelar Pedido (PUT)"
                        setOnClickListener {
                            lifecycleScope.launch {
                                order.status = OrderStatus.IN_PROGRESS
                                val repository = RestaurantRepository()

                                // CORRECCIÓN: Reutilizamos order.rawPaidDate y status "IN_PROGRESS"
                                val dto = OrderDTO(
                                    id = order.id,
                                    status = "IN_PROGRESS",
                                    restaurantId = order.restaurant?.id ?: -1,
                                    items = order.items.map { it.id },
                                    totalCost = order.totalCost,
                                    paidDate = order.rawPaidDate,
                                    deliveredDate = null
                                )
                                val res = repository.updateOrderInApi(user.email.lowercase().trim(), order.id, dto)
                                if (res != null) {
                                    Toast.makeText(this@OrderDetailActivity, "¡Pedido cancelado con éxito!", Toast.LENGTH_LONG).show()
                                    finish()
                                } else {
                                    Toast.makeText(this@OrderDetailActivity, "Error al cancelar en el servidor", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    parentLayout.addView(modifyButton, parentLayout.indexOfChild(backButton))
                    parentLayout.addView(cancelButton, parentLayout.indexOfChild(backButton))
                }
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}