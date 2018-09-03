package com.shearer.jetmoviedb.features.movie.common.repository

import com.shearer.jetmoviedb.features.movie.common.repository.dto.GenreResultDto
import com.shearer.jetmoviedb.features.movie.common.repository.dto.MovieResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


class MovieDbApi {
    interface Dao {

        @GET("movie/popular")
        fun getPopularMovies(@Query("page") page: Long): Single<MovieResultDto>

        @GET("search/movie")
        fun searchMovies(@Query("page") page: Long, @Query("query") query: String): Single<MovieResultDto>

        @GET("genre/movie/list")
        fun getMovieGenres(@Query("language") page: String = "en-US"): Single<GenreResultDto>
    }
}

