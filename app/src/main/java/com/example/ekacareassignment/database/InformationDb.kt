package com.example.ekacareassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ekacareassignment.model.Information


@Database(entities = [Information::class], version = 1)
abstract class InformationDb : RoomDatabase() {
    abstract fun informationDao(): InformationDao

    companion object {
        @Volatile
        private var INSTANCE: InformationDb? = null

        fun getDatabase(context: Context): InformationDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InformationDb::class.java,
                    "information_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}