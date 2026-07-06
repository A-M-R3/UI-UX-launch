package com.universitatcarlemany.activity3.controller

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.UserAdapter
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Order
import com.universitatcarlemany.activity3.model.entity.OrderStatus
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.model.entity.User
import com.universitatcarlemany.activity3.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val user: User? = intent.getSerializableExtra("user") as? User
        if (user == null) {
            finish()
            return
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.title = user.name.uppercase()

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener { finish() }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Consultamos la API en segundo plano
        lifecycleScope.launch(Dispatchers.IO) {
            val repository = RestaurantRepository()
            val apiOrdersDTO = repository.getUserOrdersFromApi(user.email)

            withContext(Dispatchers.Main) {
                if (apiOrdersDTO.isEmpty()) {
                    Toast.makeText(this@UserActivity, "No tienes compras finalizadas en el servidor", Toast.LENGTH_SHORT).show()
                }

                // Mapeamos los DTOs de internet al formato visual que entiende tu adaptador
                val mappedOrders = apiOrdersDTO.map { dto ->

                    val date = try {
                        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                        sdf.parse(dto.paidDate) ?: Date()
                    } catch (e: Exception) {
                        Date()
                    }

                    val dummyRestaurant = Restaurant(
                        id = dto.restaurantId,
                        name = "Restaurante (ID: ${dto.restaurantId})",
                        address = "", openingTime = "", closingTime = "", image = ""
                    )

                    val status = when (dto.status.uppercase()) {
                        "PAID" -> OrderStatus.PAID
                        "DELIVERED" -> OrderStatus.DELIVERED
                        else -> OrderStatus.IN_PROGRESS
                    }

                    // Truco: Creamos un plato fantasma con el precio total para que tu adaptador pinte bien el importe
                    val totalItem = MenuItem(
                        localId = 0, id = "dummy", name = "Total",
                        price = dto.totalCost, description = "", image = "", units = 1
                    )

                    Order(
                        user = user,
                        id = dto.id,
                        restaurant = dummyRestaurant,
                        status = status,
                        date = date,
                        items = mutableListOf(totalItem)
                    )
                }

                // Actualizamos la pantalla con los datos reales, diciendo adiós al historial local
                recyclerView.adapter = UserAdapter(user, mappedOrders)
            }
        }
    }
}