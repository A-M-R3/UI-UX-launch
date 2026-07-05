package com.universitatcarlemany.activity3.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.controller.MenuActivity
import com.universitatcarlemany.activity3.model.entity.Restaurant
import com.universitatcarlemany.activity3.model.entity.User
import java.io.Serializable

class RestaurantAdapter(
    private val restaurants: List<Restaurant>,
    private val user: User
) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.restaurant_name)
        val address: TextView = view.findViewById(R.id.restaurant_address)
        val image: ImageView = view.findViewById(R.id.restaurant_image)
        val openingTime: TextView = view.findViewById(R.id.restaurant_opening_time)
        val closingTime: TextView = view.findViewById(R.id.restaurant_closing_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val restaurant = restaurants[position]

            holder.name.text = restaurant.name
            holder.address.text = restaurant.address
            holder.openingTime.text = "Abre: ${restaurant.openingTime}"
            holder.closingTime.text = "Cierra: ${restaurant.closingTime}"

            Glide.with(holder.itemView.context).load(restaurant.image).into(holder.image)

            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, MenuActivity::class.java)

                intent.putExtra("user", user as Serializable)
                intent.putExtra("restaurant", restaurant as Serializable)
                // Se eliminó la inyección del menú aquí. Ahora se encarga el ViewModel.

                context.startActivity(intent)
            }
        } catch (e: Exception) {
            Log.e("RestaurantAdapter", "Error: ${e.message}")
        }
    }

    override fun getItemCount(): Int = restaurants.size
}