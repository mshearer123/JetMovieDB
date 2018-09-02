package com.shearer.jetmoviedb.features.movie.common.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.shearer.jetmoviedb.features.movie.common.db.MovieDb
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.common.paging.MovieBoundaryCallback
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbConstants
import com.shearer.jetmoviedb.features.movie.common.repository.MovieRepository
import com.shearer.jetmoviedb.features.movie.list.MovieListState
import com.shearer.jetmoviedb.features.movie.list.SearchInfo
import com.shearer.jetmoviedb.shared.extensions.applyIoSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

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
        val refreshTrigger = MutableLiveData<Unit>()
        val refreshState = Transformations.switchMap(refreshTrigger) {
            refresh(disposables, searchInfo)
        }
        return MovieListState(pagedList = builder.build(), refreshing = refreshState)
    }

    private fun refresh(disposables: CompositeDisposable, searchInfo: SearchInfo): LiveData<Boolean> {
        val refreshingState = MutableLiveData<Boolean>()

        when (searchInfo.type) {
            SearchInfo.Type.POPULAR -> {
                disposables += movieRepository.getPopular(1)
                        .applyIoSchedulers()
                        .subscribe({
                            dbDao.runInTransaction {
                                dbDao.movies().deleteByType("popular")
                                insertMoviesInDatabase(it, searchInfo)
                                refreshingState.value = false
                            }
                        }, {
                            refreshingState.value = false
                        })
            }
            SearchInfo.Type.SEARCH -> {
                disposables += movieRepository.getMoviesBySearchTerm(1, searchInfo.term!!)
                        .applyIoSchedulers()
                        .subscribe({
                            dbDao.runInTransaction {
                                dbDao.movies().deleteByType(searchInfo.term!!)
                                insertMoviesInDatabase(it, searchInfo)
                                refreshingState.value = false
                            }
                        }, {
                            refreshingState.value = false
                        })
            }
        }

        disposables += movieRepository.getMoviesBySearchTerm(1, searchInfo.term!!)
                .applyIoSchedulers()
                .subscribe({
                    dbDao.runInTransaction {
                        dbDao.movies().deleteByType("popular")
                        insertMoviesInDatabase(it, searchInfo)
                        refreshingState.value = false
                    }
                }, {
                    refreshingState.value = false
                })

        return refreshingState
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