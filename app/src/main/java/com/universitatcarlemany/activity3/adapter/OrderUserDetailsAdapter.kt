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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.universitatcarlemany.activity3.R
import com.universitatcarlemany.activity3.controller.OrderSummaryActivity
import com.universitatcarlemany.activity3.model.entity.MenuItem
import com.universitatcarlemany.activity3.model.entity.Order

class OrderUserDetailsAdapter(
    private val context: Context,
    private val menuItems: List<MenuItem>,
    private val order: Order
) : RecyclerView.Adapter<OrderUserDetailsAdapter.MenuViewHolder>() {
        class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemName: TextView = itemView.findViewById(R.id.item_name)
            val itemPrice: TextView = itemView.findViewById(R.id.item_price)
            val itemDescription: TextView = itemView.findViewById(R.id.item_description)
            val itemImage: ImageView = itemView.findViewById(R.id.item_image)
            val removeFromCart: Button = itemView.findViewById(R.id.remove_from_cart)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
            Log.d("OrderUserDetailsAdapter", "Entered onCreateViewHolder of OrderUserDetailsAdapter")
            val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item_summary_item, parent, false)
            return MenuViewHolder(view)
        }

        @SuppressLint("DefaultLocale", "NotifyDataSetChanged")
        override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
            try{
                Log.d("OrderUserDetailsAdapter", "Entered onBindViewHolder of OrderUserDetailsAdapter")
                val menuItem = menuItems[position]
                holder.itemName.text = menuItem.getName()
                holder.itemPrice.text = String.format("%.2f€", menuItem.getPrice())
                holder.itemDescription.text = menuItem.getDescription()

                Glide.with(holder.itemView.context).load(menuItem.getImage()).into(holder.itemImage)

                holder.removeFromCart.setOnClickListener {
                    try {
                        order.removeItem(menuItem)

                        menuItem.addUnits(-1)

                        notifyDataSetChanged()
                        (context as? OrderSummaryActivity)?.reload()
                        Log.d("OrderDetailAdapter", "${menuItem.getName()} was removed from the order")
                    } catch (e: IllegalArgumentException) {
                        Log.e("OrderDetailAdapter", "Error while removing item from the order: ${e.message}")
                    }
                }
            } catch (e: Exception) {
                Log.d("OrderUserDetailsAdapter", "Error in onBindViewHolder at position $position: ${e.message}")
            }

        }

        override fun getItemCount() = menuItems.size

}