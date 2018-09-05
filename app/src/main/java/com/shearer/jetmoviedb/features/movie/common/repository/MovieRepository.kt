package com.shearer.jetmoviedb.features.movie.common.repository

import com.shearer.jetmoviedb.features.movie.common.domain.MovieDetail
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.common.repository.dto.MovieDetailDto
import io.reactivex.Single
import io.reactivex.Single.just

interface MovieRepository {
    fun getMovieDetails(id: Int): Single<MovieDetail>
    fun getGenres(): Single<Map<Int, String>>
    fun getPopular(page: Int): Single<MovieResults>
    fun getMoviesBySearchTerm(page: Int, searchTerm: String): Single<MovieResults>
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

    override fun getMovieDetails(id: Int): Single<MovieDetail> {
        return dao.getMovieDetails(id).map(MovieDetailDto::toMovieDetails)
    }

    override fun getPopular(page: Int): Single<MovieResults> {
        return getGenres().flatMap { genres ->
            dao.getPopularMovies(page).map {
                it.toMovies(genres)
            }
        }
    }

    override fun getMoviesBySearchTerm(page: Int, searchTerm: String): Single<MovieResults> {
        return getGenres().flatMap { genres ->
            dao.searchMovies(page, searchTerm).map {
                it.toMovies(genres)
            }
        }
    }
}