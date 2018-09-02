package com.shearer.jetmoviedb.features.movie.interactor

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.db.MovieDb
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.paging.MovieBoundaryCallback
import com.shearer.jetmoviedb.features.movie.paging.MovieDataFactory
import com.shearer.jetmoviedb.features.movie.repository.MovieDbConstants
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import com.shearer.jetmoviedb.shared.extensions.applyIoSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

interface MovieInteractor {

    fun getMoviesBySearchTerm(disposables: CompositeDisposable, searchTerm: String): LiveData<PagedList<Movie>>

    fun getPopular(disposables: CompositeDisposable, refresh: Boolean = false): LiveData<PagedList<Movie>>
}

class MovieInteractorDefault(private val movieRepository: MovieRepository, private val dbDao: MovieDb) : MovieInteractor {

    override fun getMoviesBySearchTerm(disposables: CompositeDisposable, searchTerm: String): LiveData<PagedList<Movie>> {
        val popularMovieDataFactory = MovieDataFactory(searchTerm, movieRepository, disposables)
        val pagingConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build()
        return LivePagedListBuilder(popularMovieDataFactory, pagingConfig).build()
    }

    override fun getPopular(disposables: CompositeDisposable, refresh: Boolean): LiveData<PagedList<Movie>> {
        val callback = MovieBoundaryCallback(disposables, movieRepository, ::insertMoviesInDatabase)
        val dataSourceFactory = dbDao.movies().movies()
        val builder = LivePagedListBuilder(dataSourceFactory, MovieDbConstants.pageCount).setBoundaryCallback(callback)

        if (refresh) {
            refresh(disposables)
        }

        return builder.build()
    }


    private fun refresh(disposables: CompositeDisposable) {
        disposables += movieRepository.getPopular(1)
                .applyIoSchedulers()
                .subscribe({
                    dbDao.runInTransaction {
                        dbDao.movies().delete()
                    }
                }, {
                    // failure
                })
    }

    private fun insertMoviesInDatabase(movieResults: MovieResults) {
        dbDao.runInTransaction {
            val nextPage = dbDao.movies().getNexPage()
            dbDao.movies().insert(movieResults.movies.map {
                it.page = nextPage
                it
            })
        }
    }
}