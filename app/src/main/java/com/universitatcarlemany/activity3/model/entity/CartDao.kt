package com.universitatcarlemany.activity3.model.entity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItem)

    @Query("SELECT * FROM cart_items WHERE restaurantId = :restId")
    suspend fun getCartForRestaurant(restId: String): List<CartItem>

    @Query("SELECT * FROM cart_items")
    suspend fun getAllCartItems(): List<CartItem>

    @Query("DELETE FROM cart_items WHERE restaurantId = :restId")
    suspend fun clearCart(restId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearAll()

    @Query("DELETE FROM cart_items WHERE menuItemId = :itemId")
    suspend fun deleteCartItem(itemId: String)
}