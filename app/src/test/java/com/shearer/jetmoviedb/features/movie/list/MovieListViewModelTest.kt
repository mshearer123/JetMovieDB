package com.shearer.jetmoviedb.features.movie.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.DataSource
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.*
import com.shearer.jetmoviedb.Rx2SchedulersOverrideRule
import com.shearer.jetmoviedb.createPopularMovies
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test

class MovieListViewModelTest {

    @Rule @JvmField var rule = InstantTaskExecutorRule()
    @Rule @JvmField var rxRule = Rx2SchedulersOverrideRule()

    private val mockDataSource = mock<DataSource.Factory<Int, Movie>>()
    private val interactor = mock<MovieInteractor> {
        on { getMovies(any()) } doReturn Single.just(createPopularMovies())
        on { getDataSource(any()) } doReturn mockDataSource
        on { deleteMovieCache(any()) } doReturn Completable.complete()
    }
    private val model by lazy { MovieListViewModel(interactor) }

    @Test fun loadSearchTerm_callsInteractorForSearchDataSource() {
        model.loadSearchTerm("search")

        verify(interactor).getDataSource(check {
            assertThat(it).isInstanceOf(SearchConfig::class.java)
            assertThat(it.getType()).isEqualTo("s-search")
        })
    }

    @Test fun loadPopular_callsInteractorForPopularDataSource() {
        model.loadPopular()

        verify(interactor).getDataSource(check {
            assertThat(it).isInstanceOf(PopularConfig::class.java)
            assertThat(it.getType()).isEqualTo("popular")
        })
    }

    @Test fun loadMore_callsInteractorForMoveMovies() {
        model.loadMore()

        verify(interactor).getMovies(any())
    }

    @Test fun refresh_callsInteractorToDeleteMovies() {
        model.refresh()

        verify(interactor).deleteMovieCache(any())
    }

}