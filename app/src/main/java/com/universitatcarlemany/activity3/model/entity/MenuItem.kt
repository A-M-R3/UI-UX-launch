package com.universitatcarlemany.activity3.model.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "menu_items")
class MenuItem(
    @PrimaryKey(autoGenerate = true)
    private val localId: Int = 0,

    @SerializedName("id")
    private val id: String,

    @SerializedName("name")
    private val name: String,

    @SerializedName("price")
    private val price: Double,

    @SerializedName("description")
    private val description: String,

    @SerializedName("imageUrl")
    private val imageUrl: String,

    @SerializedName("units")
    private var units: Int = 0
) {
    @Ignore
    private var restaurant: Restaurant? = null

    fun getLocalId(): Int = localId
    fun getId(): String = id
    fun getName(): String = name
    fun getPrice(): Double = price
    fun getDescription(): String = description
    fun getImage(): String = imageUrl
    fun getUnits(): Int = units
    
    fun addUnits(count: Int = 1) {
        units += count
    }
    
    fun decUnits() {
        if (units > 0) units--
    }
    
    fun getRestaurant(): Restaurant? = restaurant
    fun setRestaurant(r: Restaurant?) {
        this.restaurant = r
    }
}