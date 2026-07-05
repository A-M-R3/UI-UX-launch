package com.universitatcarlemany.activity3.model.entity

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
class Restaurant(
    private val id: Int,
    private val name: String,
    private val image: String,
    private val address: String,
    private val openingTime: LocalTime,
    private val closingTime: LocalTime
) : Parcelable {
    @IgnoredOnParcel
    private var menu = Menu()

    fun getId(): Int = id

    fun getName(): String = name

    fun getImage(): String = image

    fun getAddress(): String = address

    fun getOpeningTime(): LocalTime = openingTime

    fun getClosingTime(): LocalTime = closingTime

    fun setMenu(m: Menu) { this.menu = m }

    fun getMenu(): Menu = menu
}