package com.universitatcarlemany.activity3.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.model.entity.MenuItem

class OrderDetailAdapter(
    private val menuItems: List<MenuItem>
) : RecyclerView.Adapter<OrderDetailAdapter.MenuViewHolder>() {

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val itemDescription: TextView = itemView.findViewById(R.id.item_description)
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item_historico, parent, false)
        return MenuViewHolder(view)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        try {
            val menuItem = menuItems[position]
            holder.itemName.text = menuItem.name
            holder.itemPrice.text = String.format("%.2f€", menuItem.price)
            holder.itemDescription.text = menuItem.description

            Glide.with(holder.itemView.context).load(menuItem.image).into(holder.itemImage)
        } catch (e: Exception) {
            Log.d("OrderDetailAdapter", "Error: ${e.message}")
        }
    }

    override fun getItemCount() = menuItems.size
}