package com.shearer.jetmoviedb.features.movie.repository

import com.shearer.jetmoviedb.features.movie.repository.dto.GenreResultDto
import com.shearer.jetmoviedb.features.movie.repository.dto.MovieResultDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


class MovieDbApi {
    interface Dao {
        @GET("movie/popular")
        fun getPopularMovies(@Query("page") page: String = "1"): Single<MovieResultDto>

        @GET("genres/movie/list")
        fun getMovieGenres(@Query("language") page: String = "en-US"): Single<GenreResultDto>
    }
}

