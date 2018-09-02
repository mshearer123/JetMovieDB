package com.shearer.jetmoviedb.features.movie.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shearer.jetmoviedb.features.movie.common.domain.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDb : RoomDatabase() {
    companion object {
        fun create(context: Context): MovieDb {
            val databaseBuilder = Room.databaseBuilder(context, MovieDb::class.java, "movie.db")
            return databaseBuilder.build()
        }
    }

    abstract fun movies(): MovieDao

}