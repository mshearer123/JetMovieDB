package com.shearer.jetmoviedb.features.movie.interactor

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.db.MovieDb
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.paging.MovieBoundaryCallback
import com.shearer.jetmoviedb.features.movie.repository.MovieDbConstants
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable

interface MovieInteractor {
    fun getPopular(disposables: CompositeDisposable): LiveData<PagedList<Movie>>
}

class MovieInteractorDefault(private val movieRepository: MovieRepository, private val dbDao: MovieDb) : MovieInteractor {

    override fun getPopular(disposables: CompositeDisposable): LiveData<PagedList<Movie>> {
        val callback = MovieBoundaryCallback(disposables, movieRepository, ::handleResult)
        val dataSourceFactory = dbDao.movies().movies()
        val builder = LivePagedListBuilder(dataSourceFactory, MovieDbConstants.pageCount).setBoundaryCallback(callback)
        return builder.build()
    }

    private fun handleResult(movieResults: MovieResults) {
        dbDao.runInTransaction {
            val nextPage = dbDao.movies().getNexPage()
            dbDao.movies().insert(movieResults.movies.map {
                it.page = nextPage
                it
            })
        }
    }
}