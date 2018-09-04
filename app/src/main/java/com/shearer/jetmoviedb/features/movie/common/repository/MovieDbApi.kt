package com.shearer.jetmoviedb.features.movie.common.repository

import com.shearer.jetmoviedb.features.movie.common.repository.dto.GenreResultDto
import com.shearer.jetmoviedb.features.movie.common.repository.dto.MovieDetailDto
import com.shearer.jetmoviedb.features.movie.common.repository.dto.MovieResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


class MovieDbApi {
    interface Dao {
        @GET("movie/loadPopular")
        fun getPopularMovies(@Query("page") page: Int): Single<MovieResultDto>

        @GET("search/movie")
        fun searchMovies(@Query("page") page: Int, @Query("query") query: String): Single<MovieResultDto>

        @GET("genre/movie/list")
        fun getMovieGenres(@Query("language") page: String = "en-US"): Single<GenreResultDto>

        @GET("movie/{movie_id}")
        fun getMovieDetails(@Path("movie_id") movie_id: Int): Single<MovieDetailDto>
    }
}

