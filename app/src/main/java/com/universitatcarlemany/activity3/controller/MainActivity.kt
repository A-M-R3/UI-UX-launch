package com.universitatcarlemany.activity3.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.RestaurantAdapter
import com.universitatcarlemany.activity3.model.entity.User
import com.universitatcarlemany.activity3.view.RestaurantViewModel
import java.time.LocalDate
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = User(
            "Carles", "Gallel", LocalDate.of(2000, 1, 1),
            "Calle", "+376 878 300", "carles94bcn@gmail.com",
            "https://t4.ftcdn.net/jpg/05/89/93/27/360_F_589932782_vQAEAZhHnq1QCGu5ikwrYaQD0Mmurm0N.png"
        )

        // Programacion defensiva: Si el elemento no existe en el XML, recyclerView sera null pero no colapsara
        val recyclerView: RecyclerView? = findViewById(R.id.recycler_view)

        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            Log.e("MainActivity", "ATENCION: No se encontro recycler_view en tu activity_main.xml")
        }

        // Protegemos la carga de iconos. Si no existen en tu diseno de la Actividad 2, la app simplemente los ignorara de forma segura
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
            }
        }
    }
}