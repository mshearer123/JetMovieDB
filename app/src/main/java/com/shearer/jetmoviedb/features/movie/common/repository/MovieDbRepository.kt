package com.shearer.jetmoviedb.features.movie.common.repository

import androidx.paging.DataSource
import com.shearer.jetmoviedb.features.movie.common.db.MovieDb
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.list.SearchInfo

interface MovieDbRepository {
    fun insertMoviesInDatabase(searchInfo: SearchInfo, movieResults: MovieResults)

    fun getMovieDataSource(searchInfo: SearchInfo): DataSource.Factory<Int, Movie>
}

class MovieDbRepositoryDefault(private val dbDao: MovieDb) : MovieDbRepository {

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


}