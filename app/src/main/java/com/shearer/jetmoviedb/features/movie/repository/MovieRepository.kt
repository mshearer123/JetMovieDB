package com.shearer.jetmoviedb.features.movie.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.db.MovieDb
import com.shearer.jetmoviedb.features.movie.domain.Movie
import io.reactivex.Single
import io.reactivex.Single.just
import java.util.concurrent.Executors.newSingleThreadExecutor

interface MovieRepository {
    fun getGenres(): Single<Map<Int, String>>
    fun getPopular(): LiveData<PagedList<Movie>>
}

class MovieRepositoryDefault(private val dao: MovieDbApi.Dao, private val dbDao: MovieDb) : MovieRepository {

    private lateinit var genres: Map<Int, String>


    override fun getGenres(): Single<Map<Int, String>> {
        return if (::genres.isInitialized) {
            just(genres)
        } else {
            dao.getMovieGenres().map {
                genres = it.toGenres()
                return@map genres
            }
        }
    }

    override fun getPopular(): LiveData<PagedList<Movie>> {
        val callback = MovieBoundaryCallback(dao, newSingleThreadExecutor(), ::insertMoviesIntoDb)
        val dataSourceFactory = dbDao.movies().movies()
        val builder = LivePagedListBuilder(dataSourceFactory, 20).setBoundaryCallback(callback)
        return builder.build()
    }

    private fun insertMoviesIntoDb(movies: List<Movie>) {
        dbDao.runInTransaction {
            val nextPage = dbDao.movies().getNexPage()
            dbDao.movies().insert(movies.map {
                it.page = nextPage
                it
            })
        }
    }
}