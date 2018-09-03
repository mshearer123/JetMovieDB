package com.shearer.jetmoviedb.features.movie.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shearer.jetmoviedb.features.movie.common.domain.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDb : RoomDatabase() {
    abstract fun movies(): MovieDao
}