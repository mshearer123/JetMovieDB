package com.shearer.jetmoviedb.features.movie.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.createGenreDto
import com.shearer.jetmoviedb.createPopularMoviesDto
import com.shearer.jetmoviedb.features.movie.db.MovieDb
import io.reactivex.Single
import org.junit.Test

class MovieRepositoryDefaultTest {


    private val dbDao = mock<MovieDb>()
    private val dao = mock<MovieDbApi.Dao> {
        on { getPopularMovies(any()) } doReturn Single.just(createPopularMoviesDto())
        on { getMovieGenres() } doReturn Single.just(createGenreDto())
    }

    private val repository = MovieRepositoryDefault(dao, dbDao)

    @Test
    fun getGenres_callsDao() {
        repository.getGenres()

        verify(dao).getMovieGenres()
    }

    @Test
    fun getGenres_convertsToHashMap() {
        val result = repository.getGenres().blockingGet()

        result.run {
            assertThat(this).isInstanceOf(Map::class.java)
            assertThat(size).isEqualTo(19)
            assertThat(get(28)).isEqualTo("Action")
            assertThat(get(12)).isEqualTo("Adventure")
        }
    }

}