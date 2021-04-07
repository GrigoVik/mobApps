package com.example.app

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemDB : RoomDatabase() {
    abstract fun Idao() : Idao

    companion object {
        @Volatile
        private var INSTANCE: ItemDB? = null

        fun GetDB(context: Context): ItemDB {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance;
            }

            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, ItemDB::class.java, "items_database").build()
                INSTANCE = instance
                return instance

            }


        }
    }
}