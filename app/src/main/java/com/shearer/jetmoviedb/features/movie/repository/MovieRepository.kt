package com.shearer.jetmoviedb.features.movie.repository

import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import io.reactivex.Single

interface MovieRepository {
    fun getGenres(): Single<Map<Int, String>>
    fun getPopular(): Single<MovieResults>
}

class MovieRepositoryDefault(private val dao: MovieDbApi.Dao) : MovieRepository {

    override fun getGenres(): Single<Map<Int, String>> {
        return dao.getMovieGenres().map { it.toGenres() }
    }

    override fun getPopular(): Single<MovieResults> {
        return getGenres().flatMap { genres ->
            dao.getPopularMovies().map {
                it.toMovies(genres)
            }
        }
    }
}