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
import com.universitatcarlemany.activity3.model.entity.User
import com.universitatcarlemany.activity3.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val user: User? = intent.getSerializableExtra("user") as? User ?: intent.getParcelableExtra("user", User::class.java)
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

        lifecycleScope.launch(Dispatchers.IO) {
            val repository = RestaurantRepository()
            val apiOrders = repository.getUserOrdersFromApi(user.email)

            withContext(Dispatchers.Main) {
                if (apiOrders.isEmpty()) {
                    Toast.makeText(this@UserActivity, "No tienes compras finalizadas en el servidor", Toast.LENGTH_SHORT).show()
                }
                recyclerView.adapter = UserAdapter(user, apiOrders)
            }
        }
    }
}