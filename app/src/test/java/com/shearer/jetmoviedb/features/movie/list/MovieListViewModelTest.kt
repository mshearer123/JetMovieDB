package com.shearer.jetmoviedb.features.movie.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.nhaarman.mockito_kotlin.mock
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor

class MovieListViewModelTest {

    private val mockLiveData = mock<LiveData<PagedList<Movie>>>()
    private val interactor = mock<MovieInteractor>()
    private val movieListViewModel by lazy { MovieListViewModel(interactor) }

}