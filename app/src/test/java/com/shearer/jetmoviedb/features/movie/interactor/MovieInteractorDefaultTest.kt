package com.shearer.jetmoviedb.features.movie.interactor

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.createPopularMovies
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import io.reactivex.Single
import org.junit.Test

class MovieInteractorDefaultTest {

    private val repository = mock<MovieRepository> {
        on { getPopular(any()) } doReturn Single.just(createPopularMovies())
    }

    private val interactor = MovieInteractorDefault(repository)

    @Test
    fun getPopular_callsRepository() {
        interactor.getPopular(1)

        verify(repository).getPopular(1)
    }
}