package com.shearer.jetmoviedb.features.movie.repository

import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import io.reactivex.Single
import io.reactivex.Single.just

interface MovieRepository {
    fun getGenres(): Single<Map<Int, String>>
    fun getPopular(page: Long): Single<MovieResults>
    fun getMoviesBySearchTerm(page: Long, searchTerm: String): Single<MovieResults>
}

class MovieRepositoryDefault(private val dao: MovieDbApi.Dao) : MovieRepository {


    private val genres = mutableMapOf<Int, String>()

    override fun getGenres(): Single<Map<Int, String>> {
        return if (genres.isEmpty()) {
            dao.getMovieGenres().map {
                genres.putAll(it.toGenres())
                return@map genres
            }
        } else {
            just(genres)
        }
    }

    override fun getPopular(page: Long): Single<MovieResults> {
        return getGenres().flatMap { genres ->
            dao.getPopularMovies(page).map {
                it.toMovies(genres, "popular")
            }
        }
    }

    override fun getMoviesBySearchTerm(page: Long, searchTerm: String): Single<MovieResults> {
        return getGenres().flatMap { genres ->
            dao.searchMovies(page, searchTerm).map {
                it.toMovies(genres, searchTerm)
            }
        }
    }
}