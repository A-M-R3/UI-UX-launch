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

class RestaurantAdapter(private val restaurants: List<Restaurant>, private val user: User) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.restaurant_name)
        val address: TextView = view.findViewById(R.id.restaurant_address)
        val image: ImageView = view.findViewById(R.id.restaurant_image)
        val openingTime: TextView = view.findViewById(R.id.restaurant_opening_time)
        val closingTime: TextView = view.findViewById(R.id.restaurant_closing_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("RestaurantAdapter", "onCreateViewHolder called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            Log.d("RestaurantAdapter", "onBindViewHolder called for position $position")
            holder.name.text = restaurants[position].getName()
            holder.address.text = restaurants[position].getAddress()
            holder.openingTime.text = "Abre: ${ restaurants[position].getOpeningTime()}"
            holder.closingTime.text = "Cierra: ${ restaurants[position].getClosingTime()}"

            Glide.with(holder.itemView.context).load(restaurants[position].getImage()).into(holder.image)

            holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, MenuActivity::class.java)

                intent.putExtra("user", user)
                intent.putExtra("restaurant", restaurants[position])
                intent.putExtra("menu", restaurants[position].getMenu())
                context.startActivity(intent)
            }
        } catch (e: Exception) {
            Log.d("RestaurantAdapter", "Error in onBindViewHolder at position $position: ${e.message}")
        }
    }

    override fun getItemCount(): Int {
        Log.d("RestaurantAdapter", "getItemCount called, size: ${restaurants.size}")
        return restaurants.size
    }

}