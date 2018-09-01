package com.shearer.jetmoviedb.features.movie.interactor

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.createPopularMovies
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import io.reactivex.Single
import org.junit.Test

class MovieInteractorDefaultTest {

    private val repository = mock<MovieRepository> {
        on { getPopular() } doReturn Single.just(createPopularMovies())
    }

    private val interactor = MovieInteractorDefault(repository)

    @Test
    fun getPopular_callsRepository() {
        interactor.getPopular()

        verify(repository).getPopular()
    }
}