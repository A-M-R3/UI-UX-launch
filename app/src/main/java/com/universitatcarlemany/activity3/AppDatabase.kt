package com.universitatcarlemany.activity3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.universitatcarlemany.activity3.model.entity.CartItem
import com.universitatcarlemany.activity3.model.entity.CartDao

@Database(entities = [CartItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cityxerpa_local_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}