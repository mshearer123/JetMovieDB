package com.shearer.jetmoviedb.features.movie.common.interactor

import androidx.paging.LivePagedListBuilder
import com.shearer.jetmoviedb.features.movie.common.db.MovieDb
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

class MovieInteractorDefault(private val movieRepository: MovieRepository, private val dbDao: MovieDb) : MovieInteractor {

    override fun getMovies(searchInfo: SearchInfo, disposables: CompositeDisposable): MovieListState {
        val callback = MovieBoundaryCallback(disposables, movieRepository, searchInfo) { insertMoviesInDatabase(it, searchInfo) }


        val dataSourceFactory = when (searchInfo.type) {
            SearchInfo.Type.POPULAR -> {
                dbDao.movies().moviesByType("popular")
            }
            SearchInfo.Type.SEARCH -> {
                dbDao.movies().moviesByType(searchInfo.term!!)
            }
        }

        val builder = LivePagedListBuilder(dataSourceFactory, MovieDbConstants.pageCount).setBoundaryCallback(callback)
        return MovieListState(pagedList = builder.build())
    }

    private fun insertMoviesInDatabase(movieResults: MovieResults, searchInfo: SearchInfo) {

        dbDao.runInTransaction {
            val nextPage = when (searchInfo.type) {
                SearchInfo.Type.SEARCH -> {
                    dbDao.movies().getPageByType(searchInfo.term!!) + 1
                }
                else -> {
                    dbDao.movies().getPageByType("popular") + 1
                }
            }

            dbDao.movies().insert(movieResults.movies.map {
                it.page = nextPage
                it
            })

        }
    }
}