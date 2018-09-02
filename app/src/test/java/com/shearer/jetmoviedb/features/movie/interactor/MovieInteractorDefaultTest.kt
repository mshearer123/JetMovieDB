package com.shearer.jetmoviedb.features.movie.interactor

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import org.junit.Test

class MovieInteractorDefaultTest {

    private val mockLiveData = mock<LiveData<PagedList<Movie>>>()
    private val repository = mock<MovieRepository> {
        on { getPopular() } doReturn mockLiveData
    }

    private val interactor = MovieInteractorDefault(repository)

    @Test
    fun getPopular_callsRepository() {
        interactor.getPopular()

        verify(repository).getPopular()
    }
}