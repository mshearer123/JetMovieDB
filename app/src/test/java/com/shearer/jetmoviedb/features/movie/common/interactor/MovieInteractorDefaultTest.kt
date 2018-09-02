package com.shearer.jetmoviedb.features.movie.common.interactor

import androidx.paging.DataSource
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.shearer.jetmoviedb.createPopularMovies
import com.shearer.jetmoviedb.features.movie.common.db.MovieDao
import com.shearer.jetmoviedb.features.movie.common.db.MovieDb
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class MovieInteractorDefaultTest {

    private val compositeDisposable = CompositeDisposable()

    private val source = mock<DataSource.Factory<Int, Movie>>()
    private val movieDao = mock<MovieDao> {
        on { moviesByType(any()) } doReturn source
    }
    private val dbDao = mock<MovieDb> {
        on { movies() } doReturn movieDao
    }
    private val repository = mock<MovieRepository> {
        on { getPopular(any()) } doReturn Single.just(createPopularMovies())
    }

    private val interactor = MovieInteractorDefault(repository, dbDao)

}