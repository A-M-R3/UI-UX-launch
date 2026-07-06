package com.universitatcarlemany.activity3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.universitatcarlemany.activity3.model.entity.CartItem
import com.universitatcarlemany.activity3.model.entity.CartDao

// Subimos la versión a 2 para aplicar los cambios de esquema limpios
@Database(entities = [CartItem::class], version = 2, exportSchema = false)
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
                )
                    // Instrucción CRÍTICA: Evita crasheos silenciosos si hubo cambios en tu código
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}