package com.shearer.jetmoviedb.features.movie.common.repository

import androidx.paging.DataSource
import com.shearer.jetmoviedb.features.movie.common.db.MovieDb
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.list.SearchInfo
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDbRepository {
    fun insertMoviesInDatabase(searchInfo: SearchInfo, movieResults: MovieResults)
    fun getMovieDataSource(searchInfo: SearchInfo): DataSource.Factory<Int, Movie>
    fun getNextPage(searchInfo: SearchInfo): Single<Long>
    fun deleteMovieCache(searchInfo: SearchInfo): Completable
}

class MovieDbRepositoryDefault(private val dbDao: MovieDb) : MovieDbRepository {
    override fun getNextPage(searchInfo: SearchInfo): Single<Long> {
        when (searchInfo.type) {
            SearchInfo.Type.POPULAR -> {
                return Single.fromCallable {
                    return@fromCallable dbDao.movies().getPageByType("popular").toLong() + 1
                }
            }
            SearchInfo.Type.SEARCH -> {
                return Single.fromCallable {
                    return@fromCallable dbDao.movies().getPageByType(searchInfo.term!!).toLong() + 1
                }
            }
        }
    }

    override fun insertMoviesInDatabase(searchInfo: SearchInfo, movieResults: MovieResults) {
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

    override fun getMovieDataSource(searchInfo: SearchInfo): DataSource.Factory<Int, Movie> {
        return when (searchInfo.type) {
            SearchInfo.Type.POPULAR -> {
                dbDao.movies().moviesByType("popular")
            }
            SearchInfo.Type.SEARCH -> {
                dbDao.movies().moviesByType(searchInfo.term!!)
            }
        }
    }

    override fun deleteMovieCache(searchInfo: SearchInfo): Completable {
        when (searchInfo.type) {
            SearchInfo.Type.POPULAR -> {
                return Completable.fromCallable {
                    return@fromCallable dbDao.movies().deleteByType("popular")
                }
            }
            SearchInfo.Type.SEARCH -> {
                return Completable.fromCallable {
                    return@fromCallable dbDao.movies().deleteByType(searchInfo.term!!)
                }
            }
        }
    }


}