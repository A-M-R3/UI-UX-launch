package com.universitatcarlemany.activity3.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.model.entity.Order
import com.universitatcarlemany.activity3.model.entity.User

class UserAdapter(private val user: User, private val orders: List<Order>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.user_image)
        val name: TextView = view.findViewById(R.id.user_name)
        val phone: TextView = view.findViewById(R.id.user_phone)
        val email: TextView = view.findViewById(R.id.user_email)
        val birthdate: TextView = view.findViewById(R.id.user_birth_date)
        val ordersRecyclerView: RecyclerView = view.findViewById(R.id.orders_recycler_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.name.text = user.name
            holder.email.text = user.email
            holder.phone.text = user.phoneNumber
            holder.birthdate.text = "Fecha Nacimiento: ${user.birthDate}"

            Glide.with(holder.itemView.context).load(user.image).into(holder.image)

            holder.ordersRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
            holder.ordersRecyclerView.adapter = OrderUserAdapter(holder.itemView.context, orders, user)
        } catch (e: Exception) {
            Log.d("UserAdapter", "Error: ${e.message}")
        }
    }

    override fun getItemCount(): Int {
        return 1
    }
}