package com.shearer.jetmoviedb.features.movie.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.Rx2SchedulersOverrideRule
import com.shearer.jetmoviedb.createPopularMovies
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test


class MovieListViewModelTest {
    @Rule
    @JvmField
    val rxSchedulersOverrideRule = Rx2SchedulersOverrideRule()
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val interactor = mock<MovieInteractor> {
        on { getPopular() } doReturn Single.just(createPopularMovies())
    }

    private val movieListViewModel by lazy { MovieListViewModel(interactor) }

    @Test
    fun init_callsInteractorForPopularMovies() {
        movieListViewModel.stuff()
        movieListViewModel.run {
            assertThat(movies.value).isEmpty()
            verify(interactor).getPopular()
        }
    }

    @Test
    fun init_callsPopularMovies_convertsToMovieListItems() {
        movieListViewModel.stuff()
        movieListViewModel.run {
            movies.value?.run {
                assertThat(size).isEqualTo(20)
                assertThat(get(0).title).isEqualTo("Avengers: Infinity War (2018)")
                assertThat(get(0).genres).isEqualTo("Adventure, Science Fiction, Fantasy, Action")
                assertThat(get(0).popularity).isEqualTo("220.311")
                assertThat(get(0).photoUrl).isEqualTo("https://image.tmdb.org/t/p/w342//7WsyChQLEftFiDOVTGkv3hFpyyt.jpg")
            }
        }
    }


}