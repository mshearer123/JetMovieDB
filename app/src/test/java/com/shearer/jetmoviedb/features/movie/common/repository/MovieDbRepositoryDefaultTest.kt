package com.shearer.jetmoviedb.features.movie.common.repository

import androidx.paging.DataSource
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.createPopularMovies
import com.shearer.jetmoviedb.features.movie.common.db.MovieDao
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.list.PopularConfig
import com.shearer.jetmoviedb.features.movie.list.SearchConfig
import org.junit.Test

class MovieDbRepositoryDefaultTest {

    private val mockDataSource = mock<DataSource.Factory<Int, Movie>>()
    private val movieDao = mock<MovieDao> {
        on { getPageByType(any()) } doReturn 2
        on { moviesByType(any()) } doReturn mockDataSource
    }
    private val repository = MovieDbRepositoryDefault(movieDao)

    @Test fun getNextPage_popular_callMovieDbGetNextPage_isNextPagePlus1() {
        val config = PopularConfig()
        val source = repository.getNextPage(config).blockingGet()
        assertThat(source).isEqualTo(3)
        verify(movieDao).getPageByType("loadPopular")
    }

    @Test fun getNextPage_search_callMovieDbGetNextPage_isNextPagePlus1() {
        val config = SearchConfig("search")
        val source = repository.getNextPage(config).blockingGet()
        assertThat(source).isEqualTo(3)
        verify(movieDao).getPageByType("s-search")
    }

    @Test fun insertMoviesInDatabase_callsDaoWithMovies() {
        val config = PopularConfig()
        val movies = createPopularMovies()
        repository.insertMoviesInDatabase(config, movies).blockingGet()

        verify(movieDao).insert(movies.movies)
    }

    @Test fun deleteMovieCache_popular_callsDaoDeleteByTypePopular() {
        val config = PopularConfig()
        repository.deleteMovieCache(config).blockingGet()

        verify(movieDao).deleteByType("loadPopular")
    }

    @Test fun deleteMovieCache_search_callsDaoDeleteByTypeSearchTerm() {
        val config = SearchConfig("search")
        repository.deleteMovieCache(config).blockingGet()

        verify(movieDao).deleteByType("s-search")
    }

    @Test fun getMovieDataSource_popular_callsDaoForSource() {
        val config = PopularConfig()
        repository.getMovieDataSource(config)

        verify(movieDao).moviesByType("loadPopular")
    }

    @Test fun getMovieDataSource_search_callsDaoForSource() {
        val config = SearchConfig("search")
        repository.getMovieDataSource(config)

        verify(movieDao).moviesByType("s-search")
    }

}