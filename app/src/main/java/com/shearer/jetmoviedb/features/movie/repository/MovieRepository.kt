package com.shearer.jetmoviedb.features.movie.repository

import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import io.reactivex.Single
import io.reactivex.Single.just

interface MovieRepository {
    fun getGenres(): Single<Map<Int, String>>
    fun getPopular(page: Long): Single<MovieResults>
}

class MovieRepositoryDefault(private val dao: MovieDbApi.Dao) : MovieRepository {

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

    override fun getPopular(page: Long): Single<MovieResults> {
        return getGenres().flatMap { genres ->
            dao.getPopularMovies(page).map {
                it.toMovies(genres)
            }
        }
    }
}