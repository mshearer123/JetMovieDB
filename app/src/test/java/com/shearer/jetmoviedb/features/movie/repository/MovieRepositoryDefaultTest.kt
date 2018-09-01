package com.shearer.jetmoviedb.features.movie.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.createGenreDto
import com.shearer.jetmoviedb.createPopularMoviesDto
import io.reactivex.Single
import org.junit.Test

class MovieRepositoryDefaultTest {

    private val dao = mock<MovieDbApi.Dao> {
        on { getPopularMovies() } doReturn Single.just(createPopularMoviesDto())
        on { getMovieGenres() } doReturn Single.just(createGenreDto())
    }

    private val repository = MovieRepositoryDefault(dao)

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

    @Test
    fun getPopular_callsDao() {
        repository.getPopular().blockingGet()

        verify(dao).getPopularMovies()
    }

    @Test
    fun getPopular_callsGenresPriorToMovies() {
        repository.getPopular().blockingGet()

        inOrder(dao) {
            verify(dao).getMovieGenres()
            verify(dao).getPopularMovies()
        }
    }
}