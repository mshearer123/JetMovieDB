package com.shearer.jetmoviedb.features.movie.common.repository

import androidx.paging.DataSource
import com.shearer.jetmoviedb.features.movie.common.db.MovieDao
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.list.ListConfig
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDbRepository {
    fun getNextPage(config: ListConfig): Single<Int>
    fun insertMoviesInDatabase(config: ListConfig, movieResults: MovieResults): Completable
    fun deleteMovieCache(config: ListConfig): Completable
    fun getMovieDataSource(config: ListConfig): DataSource.Factory<Int, Movie>
}

class MovieDbRepositoryDefault(private val dbDao: MovieDao) : MovieDbRepository {
    override fun getNextPage(config: ListConfig): Single<Int> {
        return Single.fromCallable {
            return@fromCallable dbDao.getPageByType(config.getType()) + 1
        }
    }

    override fun insertMoviesInDatabase(config: ListConfig, movieResults: MovieResults): Completable {
        return Completable.fromCallable {
            val nextPage = dbDao.getPageByType(config.getType()) + 1

            val movies = movieResults.movies.map {
                it.type = config.getType()
                it.page = nextPage
                return@map it
            }
            dbDao.insert(movies)
        }
    }

    override fun deleteMovieCache(config: ListConfig): Completable {
        return Completable.fromCallable {
            return@fromCallable dbDao.deleteByType(config.getType())
        }
    }

    override fun getMovieDataSource(config: ListConfig) = dbDao.moviesByType(config.getType())

}