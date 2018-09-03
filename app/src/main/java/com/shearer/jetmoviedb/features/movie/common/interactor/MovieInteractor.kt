package com.shearer.jetmoviedb.features.movie.common.interactor

import androidx.paging.LivePagedListBuilder
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.common.paging.MovieBoundaryCallback
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbConstants
import com.shearer.jetmoviedb.features.movie.common.repository.MovieRepository
import com.shearer.jetmoviedb.features.movie.list.MovieListState
import com.shearer.jetmoviedb.features.movie.list.SearchInfo
import io.reactivex.disposables.CompositeDisposable

interface MovieInteractor {
    fun getMovies(searchInfo: SearchInfo, disposables: CompositeDisposable): MovieListState
}

class MovieInteractorDefault(private val movieRepository: MovieRepository, private val movieDbInteractor: MovieDbInteractor) : MovieInteractor {

    override fun getMovies(searchInfo: SearchInfo, disposables: CompositeDisposable): MovieListState {
        val callback = MovieBoundaryCallback(disposables, movieRepository, searchInfo) { insertMoviesInDatabase(it, searchInfo) }
        val dataSource = movieDbInteractor.getMovieDataSource(searchInfo)
        val builder = LivePagedListBuilder(dataSource, MovieDbConstants.pageCount).setBoundaryCallback(callback)
        return MovieListState(builder.build())
    }

    private fun insertMoviesInDatabase(movieResults: MovieResults, searchInfo: SearchInfo) {
        movieDbInteractor.insertMoviesInDatabase(searchInfo, movieResults)
    }
}