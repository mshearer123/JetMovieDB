package com.shearer.jetmoviedb.features.movie.common.interactor

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.createPopularMovies
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbRepository
import com.shearer.jetmoviedb.features.movie.common.repository.MovieRepository
import com.shearer.jetmoviedb.features.movie.list.PopularConfig
import com.shearer.jetmoviedb.features.movie.list.SearchConfig
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

class MovieInteractorDefaultTest {

    private val repository = mock<MovieRepository> {
        on { getPopular(any()) } doReturn Single.just(createPopularMovies())
        on { getMoviesBySearchTerm(any(), any()) } doReturn Single.just(createPopularMovies())
    }

    private val dbRepository = mock<MovieDbRepository> {
        on { getNextPage(any()) } doReturn Single.just(2)
        on { insertMoviesInDatabase(any(), any()) } doReturn Completable.complete()
    }

    private val interactor = MovieInteractorDefault(repository, dbRepository)

    @Test fun getMovieDetails_callsMovieRepositoryGetMovieDetails() {

        interactor.getMovieDetails(1)

        verify(repository).getMovieDetails(1)
    }

    @Test fun getMovies_popular_retrieveNextPageForPopularMoviesFromRepository() {
        val config = PopularConfig()

        interactor.getMovies(config).blockingGet()

        verify(dbRepository).getNextPage(config)
        verify(repository).getPopular(2)
    }

    @Test fun getMovies_search_retrieveNextPageForSearchFromRepository() {
        val config = SearchConfig("search")

        interactor.getMovies(config).blockingGet()

        verify(dbRepository).getNextPage(config)
        verify(repository).getMoviesBySearchTerm(2, "search")
    }

    @Test fun deleteMovieCache_search_callsDbRepositoryDeleteMovieCache() {
        val config = SearchConfig("search")
        interactor.deleteMovieCache(config)

        verify(dbRepository).deleteMovieCache(config)
    }

    @Test fun deleteMovieCache_popular_callsDbRepositoryDeleteMovieCache() {
        val config = PopularConfig()
        interactor.deleteMovieCache(config)

        verify(dbRepository).deleteMovieCache(config)
    }

    @Test fun getDataSource_search_callsDbRepositoryGetDataSource() {
        val config = SearchConfig("search")
        interactor.getDataSource(config)

        verify(dbRepository).getMovieDataSource(config)
    }

    @Test fun getDataSource_popular_callsDbRepositoryGetDataSource() {
        val config = PopularConfig()
        interactor.getDataSource(config)

        verify(dbRepository).getMovieDataSource(config)
    }
}