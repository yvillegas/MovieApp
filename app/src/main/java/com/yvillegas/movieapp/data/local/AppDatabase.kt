package com.yvillegas.movieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yvillegas.movieapp.data.model.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 4)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "movie_table"
            ).fallbackToDestructiveMigration().build()

            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}