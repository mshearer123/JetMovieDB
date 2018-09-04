package com.shearer.jetmoviedb.features.movie.common.repository

import androidx.paging.DataSource
import com.shearer.jetmoviedb.features.movie.common.db.MovieDb
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.list.ListConfig
import io.reactivex.Completable
import io.reactivex.Single

interface MovieDbRepository {
    fun insertMoviesInDatabase(config: ListConfig, movieResults: MovieResults): Completable
    fun getNextPage(config: ListConfig): Single<Long>
    fun deleteMovieCache(config: ListConfig): Completable
    fun getMovieDataSource(config: ListConfig): DataSource.Factory<Int, Movie>
}

class MovieDbRepositoryDefault(private val dbDao: MovieDb) : MovieDbRepository {
    override fun getNextPage(config: ListConfig): Single<Long> {
        return Single.fromCallable {
            return@fromCallable dbDao.movies().getPageByType(config.getType()).toLong() + 1
        }
    }

    override fun insertMoviesInDatabase(config: ListConfig, movieResults: MovieResults): Completable {
        return Completable.fromCallable {
            val nextPage = dbDao.movies().getPageByType(config.getType()) + 1
            val movies = movieResults.movies.map {
                it.type = config.getType()
                it.page = nextPage
                return@map it
            }.toList()
            dbDao.movies().insert(movies)
        }
    }

    override fun deleteMovieCache(config: ListConfig): Completable {
        return Completable.fromCallable {
            return@fromCallable dbDao.movies().deleteByType(config.getType())
        }
    }

    override fun getMovieDataSource(config: ListConfig) = dbDao.movies().moviesByType(config.getType())

}