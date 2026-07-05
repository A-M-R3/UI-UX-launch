package com.universitatcarlemany.activity3.controller

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.view.MenuViewModel
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.MenuAdapter
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.model.entity.User

class MenuActivity : ComponentActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Actualizado para recoger objetos Serializable de forma correcta y evitar fallos de Casting
        val restaurant = intent.getSerializableExtra("restaurant") as? Restaurant
        if (restaurant == null) {
            finish()
            return
        }

        val user = intent.getSerializableExtra("user") as? User
        if (user == null) {
            finish()
            return
        }

        // Programacion defensiva en las vistas
        val recyclerView: RecyclerView? = findViewById(R.id.recycler_view)
        recyclerView?.layoutManager = LinearLayoutManager(this)

        val toolbar: androidx.appcompat.widget.Toolbar? = findViewById(R.id.toolbar)
        toolbar?.title = "MENU DE " + restaurant.name.uppercase()

        val backButton: Button? = findViewById(R.id.back_button)
        backButton?.setOnClickListener {
            finish()
        }

        val menuViewModel = MenuViewModel(restaurant)

        menuViewModel.items.observe(this) { items ->
            if (items != null) {
                recyclerView?.adapter = MenuAdapter(items, user)
            }
        }
    }
}