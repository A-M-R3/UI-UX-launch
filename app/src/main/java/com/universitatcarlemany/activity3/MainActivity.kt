package com.universitatcarlemany.activity3

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.universitatcarlemany.activity3.view.RestaurantViewModel
import com.universitatcarlemany.activity3.adapter.RestaurantAdapter
import com.universitatcarlemany.activity3.controller.OrderSummaryActivity
import com.universitatcarlemany.activity3.controller.UserActivity
import com.universitatcarlemany.activity3.model.entity.User
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    private val viewModel: RestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = "carles94bcn@gmail.com"
        val user = User("Carles", "Gallel", LocalDate.of(2000, 1, 1), "Calle", "+376 878 300", email, "https://t4.ftcdn.net/jpg/05/89/93/27/360_F_589932782_vQAEAZhHnq1QCGu5ikwrYaQD0Mmurm0N.png")

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val toolbarTitle: TextView = findViewById(R.id.toolbar_title)
        toolbarTitle.text = "RESTAURANTES"

        val userIcon: ImageView = findViewById(R.id.user_icon)
        userIcon.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("user", user)

            startActivity(intent)
        }

        val cartIcon: ImageView = findViewById(R.id.cart_icon)
        cartIcon.setOnClickListener {
            val intent = Intent(this, OrderSummaryActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }

        // [COMMENT] Load your restaurants here from the API

        // [COMMENT] Get your orders here from the database

    }


}

