package com.universitatcarlemany.activity3.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Parcelable
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
import java.text.SimpleDateFormat
import java.util.Locale

class OrderUserAdapter(
    private val context: Context,
    private val orders: List<Order>,
    private val user: User
) : RecyclerView.Adapter<OrderUserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderRestaurant: TextView = view.findViewById(R.id.order_restaurant)
        val orderID: TextView = view.findViewById(R.id.order_id)
        val orderPaidDate: TextView = view.findViewById(R.id.order_paiddate)
        val orderStatus: TextView = view.findViewById(R.id.order_status)
        val orderTotal: TextView = view.findViewById(R.id.order_total)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        val restaurant = order.restaurant

        if (restaurant != null) {
            holder.orderRestaurant.text = restaurant.name
        }

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
        val formattedDate = sdf.format(order.date)
        holder.orderPaidDate.text = "Fecha Pedido: $formattedDate"

        holder.orderID.text = "Identificador: " + order.id.toString()
        holder.orderTotal.text = "Total: €${String.format("%.2f", order.totalCost)}"
        holder.orderStatus.text = "Estado: ${order.status.toSpanish()}"
        holder.orderStatus.setStatusTextColor(order.status)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, OrderDetailActivity::class.java)
            intent.putExtra("order_id", order.id)
            intent.putExtra("user", user as Parcelable)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = orders.size
}