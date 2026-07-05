package com.universitatcarlemany.activity3.controller

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.view.MenuViewModel
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.MenuAdapter
import com.universitatcarlemany.activity3.model.entity.Menu
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.model.entity.User

class MenuActivity : ComponentActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val restaurant: Restaurant? = intent.getParcelableExtra("restaurant", Restaurant::class.java)

        if (restaurant == null) {
            Log.e("MenuActivity", "Restaurant is null")
            finish()
            return
        }

        val menu: Menu? = intent.getParcelableExtra("menu", Menu::class.java)

        if (menu == null) {
            Log.e("MenuActivity", "Menu is null")
            finish()
            return
        }

        val user: User? = intent.getParcelableExtra("user", User::class.java)

        if (user == null) {
            Log.e("UserActivity", "User is null")
            finish()
            return
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        menu.addAllItems(restaurant.getMenu().getAllItems())
        restaurant.setMenu(menu)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "MENÚ DE " + restaurant.getName().uppercase()

        val strAllergies = restaurant.getMenu().getAllergies()
        val allergiesTextView: TextView = findViewById(R.id.alergias_text)
        allergiesTextView.text = "Información sobre alergias: $strAllergies"

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val menuViewModel = MenuViewModel(restaurant)

        menuViewModel.items.observe(this) { items ->
            recyclerView.adapter = MenuAdapter(items, user)
        }
    }
}