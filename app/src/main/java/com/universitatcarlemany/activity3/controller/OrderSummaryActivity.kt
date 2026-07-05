package com.universitatcarlemany.activity3.controller


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.adapter.OrderUserDetailsAdapter
import com.universitatcarlemany.activity3.model.entity.User

class OrderSummaryActivity : ComponentActivity() {

    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Resumen del Pedido"

        val user: User? = intent.getParcelableExtra("user", User::class.java)

        if (user == null) {
            Log.e("OrderSummaryActivity", "User is null")
            finish()
            return
        }

        val order = OrderManager.getOrder(user)

        if (order != null) {
            val recyclerView = findViewById<RecyclerView>(R.id.order_items_recyclerview)
            recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = OrderUserDetailsAdapter(this,order.getItems(),order)
            recyclerView.adapter = adapter

            val totalTextView = findViewById<TextView>(R.id.order_total_text)
            totalTextView.text = "Total: €${String.format("%.2f", order.getTotalCost())}"

            val finalizeOrderButton: Button = findViewById(R.id.finalize_order_button)
            finalizeOrderButton.setOnClickListener {
                OrderManager.saverOrder(order)
                Toast.makeText(this, "Pedido guardado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }

            if (order.getItems().isEmpty()) {
                finalizeOrderButton.visibility = View.GONE
                Toast.makeText(this, "Pedido sin artículos, agregue platos", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                finalizeOrderButton.visibility = View.VISIBLE
            }

            val backButton: Button = findViewById(R.id.back_button)
            backButton.setOnClickListener {
                finish()
            }
        } else {
            Toast.makeText(this, "No hay ningún pedido en progreso", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun reload() {
        finish()
        startActivity(intent)
    }
}
