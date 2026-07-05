package com.universitatcarlemany.activity3.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.controller.OrderDetailActivity
import com.universitatcarlemany.activity3.model.entity.Order
import com.universitatcarlemany.activity3.model.entity.User
import com.universitatcarlemany.activity3.model.entity.setStatusTextColor
import com.universitatcarlemany.activity3.model.entity.toSpanish
import java.time.format.DateTimeFormatter

class OrderUserAdapter(
    private val context: Context,
    private val orders: List<Order>,
    private val user: User
) : RecyclerView.Adapter<OrderUserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderRestaurant: TextView = view.findViewById(R.id.order_restaurant)
        val orderID: TextView = view.findViewById(R.id.order_id)
        val order_paiddate: TextView = view.findViewById(R.id.order_paiddate)
        val orderStatus: TextView = view.findViewById(R.id.order_status)
        val orderTotal: TextView = view.findViewById(R.id.order_total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        val restaurant = order.getRestaurant()

        if (restaurant != null) {
            holder.orderRestaurant.text = restaurant.getName()
        }
        val spanishDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatedDate = order.getPaidDate()?.format(spanishDateFormatter)

        if (formatedDate != null) {
            holder.order_paiddate.text = "Fecha Pedido: $formatedDate"
        }

        holder.orderID.text = "Identificador: " + order.getId().toString()
        holder.orderTotal.text = "Total: €${String.format("%.2f", order.getTotalCost())}"
        holder.orderStatus.text = "Estado: ${order.getStatus().toSpanish()}"
        holder.orderStatus.setStatusTextColor(order.getStatus())

        holder.itemView.setOnClickListener {
            val intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra("order_id", order.getId())
            intent.putExtra("user", user)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = orders.size
}
