package com.universitatcarlemany.activity3.model.entity

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
class Menu(
    private var allergies: String = ""
) : Parcelable {
    @IgnoredOnParcel
    val items = mutableListOf<MenuItem>()

    fun getAllergies(): String {
        return allergies
    }

    fun setAllergies(allergies: String) {
        this.allergies = allergies
    }

    fun addItem(item: MenuItem) {
        items.add(item)
    }

    fun removeItem(item: MenuItem) {
        items.remove(item)
    }

    fun getAllItems(): List<MenuItem> {
        return items
    }

    fun addAllItems(items: List<MenuItem>) {
        this.items.addAll(items)
    }
}
