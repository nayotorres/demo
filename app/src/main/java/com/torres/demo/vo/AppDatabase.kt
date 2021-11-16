package com.torres.demo.vo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.torres.demo.data.model.MovieDao
import com.torres.demo.domain.DataDao

@Database(entities = arrayOf(MovieDao::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accessDao(): DataDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
               "demo"
            ).build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}