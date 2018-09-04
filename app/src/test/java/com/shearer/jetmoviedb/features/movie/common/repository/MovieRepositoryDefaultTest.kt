package com.shearer.jetmoviedb.features.movie.common.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.createGenreDto
import com.shearer.jetmoviedb.createMovieDetailsDto
import com.shearer.jetmoviedb.createPopularMoviesDto
import io.reactivex.Single
import org.junit.Test

class MovieRepositoryDefaultTest {

    private val movieDetailsDto = createMovieDetailsDto()
    private val popularMoviesDto = createPopularMoviesDto()
    private val searchMoviesDto = createPopularMoviesDto()
    private val genresDto = createGenreDto()

    private val dao = mock<MovieDbApi.Dao> {
        on { getMovieDetails(any()) } doReturn Single.just(movieDetailsDto)
        on { getPopularMovies(any()) } doReturn Single.just(popularMoviesDto)
        on { searchMovies(any(), any()) } doReturn Single.just(searchMoviesDto)
        on { getMovieGenres() } doReturn Single.just(genresDto)
    }

    private val repository = MovieRepositoryDefault(dao)

    @Test fun getGenres_callsDao() {
        repository.getGenres()

        verify(dao).getMovieGenres()
    }

    @Test fun getGenres_convertsToHashMap() {
        val result = repository.getGenres().blockingGet()

        result.run {
            assertThat(this).isInstanceOf(Map::class.java)
            assertThat(size).isEqualTo(19)
            assertThat(get(28)).isEqualTo("Action")
            assertThat(get(12)).isEqualTo("Adventure")
        }
    }

    @Test fun getMovieDetails_callsDao_convertsToDomain() {
        val result = repository.getMovieDetails(1).blockingGet()

        verify(dao).getMovieDetails(1)
        assertThat(result).isEqualTo(movieDetailsDto.toMovieDetails())
    }

    @Test fun getPopular_callsDao_convertsToDomain() {
        val result = repository.getPopular(1).blockingGet()

        verify(dao).getPopularMovies(1)
        assertThat(result).isEqualTo(popularMoviesDto.toMovies(genresDto.toGenres()))
    }

    @Test fun getMoviesBySearchTerm_callsDao_convertsToDomain() {
        val result = repository.getMoviesBySearchTerm(1, "search").blockingGet()

        verify(dao).searchMovies(1, "search")
        assertThat(result).isEqualTo(searchMoviesDto.toMovies(genresDto.toGenres()))
    }

}