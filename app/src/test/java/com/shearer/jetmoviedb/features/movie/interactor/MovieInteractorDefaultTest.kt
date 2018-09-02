package com.shearer.jetmoviedb.features.movie.interactor

import androidx.paging.DataSource
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.shearer.jetmoviedb.createPopularMovies
import com.shearer.jetmoviedb.features.movie.db.MovieDao
import com.shearer.jetmoviedb.features.movie.db.MovieDb
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class MovieInteractorDefaultTest {

    private val compositeDisposable = CompositeDisposable()

    private val source = mock<DataSource.Factory<Int, Movie>>()
    private val movieDao = mock<MovieDao> {
        on { movies() } doReturn source
    }
    private val dbDao = mock<MovieDb> {
        on { movies() } doReturn movieDao
    }
    private val repository = mock<MovieRepository> {
        on { getPopular(any()) } doReturn Single.just(createPopularMovies())
    }

    private val interactor = MovieInteractorDefault(repository, dbDao)

}