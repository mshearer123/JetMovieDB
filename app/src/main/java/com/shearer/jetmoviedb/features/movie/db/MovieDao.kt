package com.shearer.jetmoviedb.features.movie.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shearer.jetmoviedb.features.movie.domain.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<Movie>)

    @Query("SELECT * FROM movies")
    fun movies(): DataSource.Factory<Int, Movie>

    @Query("DELETE FROM movies")
    fun delete()

    @Query("SELECT MAX(page) + 1 FROM movies")
    fun getNexPage(): Int

}