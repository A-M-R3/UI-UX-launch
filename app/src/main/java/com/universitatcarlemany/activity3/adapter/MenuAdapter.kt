package com.universitatcarlemany.activity3.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.universitatcarlemany.activity3.controller.OrderManager
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.User
import kotlinx.coroutines.launch


class MenuAdapter(private val menuItems: List<MenuItem>,
                  private val user: User
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

        class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemName: TextView = itemView.findViewById(R.id.item_name)
            val itemPrice: TextView = itemView.findViewById(R.id.item_price)
            val itemUnits: TextView = itemView.findViewById(R.id.item_units)
            val itemDescription: TextView = itemView.findViewById(R.id.item_description)
            val itemImage: ImageView = itemView.findViewById(R.id.item_image)
            val addToCartButton: Button = itemView.findViewById(R.id.add_to_cart_button)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
            Log.d("MenuAdapter", "Entering onCreateViewHolder")
            val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
            return MenuViewHolder(view)
        }

        @SuppressLint("DefaultLocale", "SetTextI18n")
        override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {

            try{
                Log.d("MenuAdapter", "Entering onBindViewHolder")
                val menuItem = menuItems[position]

                holder.itemName.text = menuItem.getName()
                holder.itemPrice.text = String.format("%.2f€", menuItem.getPrice())
                holder.itemDescription.text = menuItem.getDescription()
                holder.itemUnits.text = "Stock: "+ menuItem.getUnits().toString()

                Glide.with(holder.itemView.context).load(menuItem.getImage()).into(holder.itemImage)

                holder.addToCartButton.setOnClickListener {
                    onMenuItemClick(holder.itemView.context, menuItem, user)
                }

                holder.itemView.setOnClickListener {
                    onMenuItemClick(holder.itemView.context, menuItem, user)
                }

            } catch (e: Exception) {
                Log.d("MenuAdapter", "Error in onBindViewHolder at position $position: ${e.message}")
            }

        }

        @SuppressLint("DefaultLocale")
        private fun onMenuItemClick(
            context: Context,
            menuItem: MenuItem,
            user: User
        ) {
            Log.d("MenuAdapter", "Entering handleMenuItemClick")
            if (menuItem.getUnits() > 0){

                val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                builder.setTitle("¿Agregar al pedido?")
                builder.setMessage("¿Agregar ${menuItem.getName()} al pedido?\n\nPrecio: ${String.format("%.2f€", menuItem.getPrice())}")

                builder.setPositiveButton("Add to order") { dialog, _ ->
                    var order = OrderManager.getOrder(user)
                    if (order == null) {
                        order = OrderManager.createOrder(user, menuItem.getRestaurant())
                    }

                    try {
                        OrderManager.addItemToOrder(order, menuItem)
                        Toast.makeText(context, "${menuItem.getName()} added to order", Toast.LENGTH_SHORT).show()
                        menuItem.decUnits()
                    } catch (e: IllegalArgumentException) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }

                    // [COMMENT] Save or update the order to local database


                    dialog.dismiss()
                }

                builder.setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }

        override fun getItemCount() = menuItems.size

}