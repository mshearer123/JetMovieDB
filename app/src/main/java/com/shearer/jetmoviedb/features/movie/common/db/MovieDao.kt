package com.shearer.jetmoviedb.features.movie.common.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shearer.jetmoviedb.features.movie.common.domain.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<Movie>)

    @Query("SELECT * FROM movies WHERE type = :type")
    fun moviesByType(type: String): DataSource.Factory<Int, Movie>

    @Query("DELETE FROM movies WHERE type = :type")
    fun deleteByType(type: String)

    @Query("SELECT MAX(page) FROM movies WHERE type = :type")
    fun getPageByType(type: String): Int

}