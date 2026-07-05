package com.universitatcarlemany.activity3.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.RestaurantAdapter
import com.universitatcarlemany.activity3.view.RestaurantViewModel
import com.universitatcarlemany.activity3.model.entity.User
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 1. Inicializamos el ViewModel (que automáticamente llama a la API por debajo)
        viewModel = ViewModelProvider(this)[RestaurantViewModel::class.java]

        // 2. EL PASO CRÍTICO: Observamos los datos que llegan de Internet
        viewModel.restaurants.observe(this) { listaRestaurantes ->

            // Si la lista llega con datos del servidor, la mostramos en pantalla
            if (listaRestaurantes.isNotEmpty()) {
                val user = User("Carles", "Gallel", LocalDate.of(2000, 1, 1), "Calle", "+376 878 300", "carles94bcn@gmail.com", "https://t4.ftcdn.net/jpg/05/89/93/27/360_F_589932782_vQAEAZhHnq1QCGu5ikwrYaQD0Mmurm0N.png")
                val adapter = RestaurantAdapter(listaRestaurantes, user)
                recyclerView.adapter = adapter
            }
        }
    }
}