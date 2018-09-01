package com.shearer.jetmoviedb.features.movie.list

import com.nhaarman.mockito_kotlin.mock
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor

class MovieListViewModelTest {

    private val interactor = mock<MovieInteractor>()
    private val movieListViewModel by lazy { MovieListViewModel(interactor) }

}