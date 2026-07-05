package com.universitatcarlemany.activity3.model.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "menu_items")
data class MenuItem(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,

    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("description")
    val description: String,

    @SerializedName("imageUrl")
    val image: String,

    @SerializedName("units")
    var units: Int = 0
) : Serializable {

    @Ignore
    var restaurant: Restaurant? = null

    fun addUnits(count: Int = 1) {
        units += count
    }

    fun decUnits() {
        if (units > 0) units--
    }
}