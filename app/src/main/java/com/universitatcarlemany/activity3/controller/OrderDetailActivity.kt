package com.universitatcarlemany.activity3.controller

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.OrderDetailAdapter
import com.universitatcarlemany.activity3.model.entity.User

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
            }
        }

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }
    }
}