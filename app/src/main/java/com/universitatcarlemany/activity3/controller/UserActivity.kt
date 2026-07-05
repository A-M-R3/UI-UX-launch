package com.universitatcarlemany.activity3.controller

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.UserAdapter
import com.universitatcarlemany.activity3.model.entity.User

class UserActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val user: User? = intent.getParcelableExtra("user", User::class.java)

        if (user == null) {
            finish()
            return
        }

        val orders = user.getAllOrders()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.title = user.name.uppercase()

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val adapter = UserAdapter(user, orders.toList())
        recyclerView.adapter = adapter
    }
}