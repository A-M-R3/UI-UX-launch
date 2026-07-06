package com.universitatcarlemany.activity3.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.AppDatabase
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.RestaurantAdapter
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.User
import com.universitatcarlemany.activity3.view.RestaurantViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val miCorreoUniversitario = "adrianmeneses@universitatcarlemany.com"

        val user = User(
            "Adrián", "Meneses", LocalDate.of(2000, 1, 1),
            "Calle", "+376 878 300", miCorreoUniversitario,
            "https://t4.ftcdn.net/jpg/05/89/93/27/360_F_589932782_vQAEAZhHnq1QCGu5ikwrYaQD0Mmurm0N.png"
        )

        val recyclerView: RecyclerView? = findViewById(R.id.recycler_view)
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        val toolbarTitle: TextView? = findViewById(R.id.toolbar_title)
        toolbarTitle?.text = "RESTAURANTES"

        val userIcon: ImageView? = findViewById(R.id.user_icon)
        userIcon?.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("user", user as Serializable)
            startActivity(intent)
        }

        val cartIcon: ImageView? = findViewById(R.id.cart_icon)
        cartIcon?.setOnClickListener {
            val intent = Intent(this, OrderSummaryActivity::class.java)
            intent.putExtra("user", user as Serializable)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this)[RestaurantViewModel::class.java]

        viewModel.restaurants.observe(this) { listaRestaurantes ->
            if (listaRestaurantes != null && listaRestaurantes.isNotEmpty() && recyclerView != null) {
                val adapter = RestaurantAdapter(listaRestaurantes, user)
                recyclerView.adapter = adapter

                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val db = AppDatabase.getDatabase(this@MainActivity)
                        val pendingItems = db.cartDao().getAllCartItems()

                        if (pendingItems.isNotEmpty()) {
                            withContext(Dispatchers.Main) {
                                val restIdStr = pendingItems.first().restaurantId
                                val restId = restIdStr.toIntOrNull() ?: 0

                                val targetRestaurant = listaRestaurantes.find { it.id == restId } ?: listaRestaurantes.firstOrNull()

                                if (targetRestaurant != null) {
                                    var order = OrderManager.getOrder(user)
                                    if (order == null) {
                                        order = OrderManager.createOrder(user, targetRestaurant)
                                    }

                                    if (order.items.isEmpty()) {
                                        pendingItems.forEach { cartItem ->
                                            val restoredMenuItem = MenuItem(
                                                id = cartItem.menuItemId,
                                                name = cartItem.name,
                                                price = cartItem.price,
                                                description = "Plato recuperado",
                                                image = "",
                                                units = cartItem.quantity
                                            )
                                            restoredMenuItem.restaurant = targetRestaurant
                                            OrderManager.addItemToOrder(order, restoredMenuItem)
                                        }
                                        Toast.makeText(this@MainActivity, "Pedido recuperado con éxito", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error al acceder a Room DB: ${e.message}")
                    }
                }
            }
        }
    }
}