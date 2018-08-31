package com.shearer.jetmoviedb.features.movie.repository

import com.shearer.jetmoviedb.features.movie.domain.Genre
import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import io.reactivex.Single

interface MovieRepository {
    fun getGenres(): Single<List<Genre>>
    fun getPopular(): Single<MovieResults>
}

class MovieRepositoryDefault(private val dao: MovieDbApi.Dao) : MovieRepository {

    override fun getGenres(): Single<List<Genre>> {
        return dao.getMovieGenres().map { it.toGenres() }
    }

    override fun getPopular(): Single<MovieResults> {
        return dao.getPopularMovies().map { it.toMovies() }
    }
}