package com.shearer.jetmoviedb.features.movie.list

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.RxImmediateSchedulerRule
import com.shearer.jetmoviedb.createPopularMovies
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import io.reactivex.Single
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test


class MovieListViewModelTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Rule
    @JvmField
    val rule = RxImmediateSchedulerRule()

    private val interactor = mock<MovieInteractor> {
        on { getPopular() } doReturn Single.just(createPopularMovies())
    }

    private val movieListViewModel by lazy { MovieListViewModel(interactor) }

    @Test
    fun init_callsInteractorForPopularMovies() {
        movieListViewModel.run {
            verify(interactor).getPopular()
        }
    }


}