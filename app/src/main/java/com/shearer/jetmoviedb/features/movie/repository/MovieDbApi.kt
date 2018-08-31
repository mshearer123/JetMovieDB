package com.shearer.jetmoviedb.features.movie.repository

import com.shearer.jetmoviedb.features.movie.domain.Genre
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


class MovieDbApi {
    interface Dao {
        @GET("movie/popular")
        fun getPopularMovies(@Query("page") page: String = "1"): Single<MovieResultDto>

        @GET("genre/movie/list")
        fun getMovieGenres(@Query("language") page: String = "en-US"): Single<GenreResultDto>
    }

    data class MovieResultDto(
            val page: Int,
            val total_results: Int,
            val total_pages: Int,
            val results: List<MovieDto>
    ) {

        fun toMovies(): MovieResults {
            return MovieResults(page, total_results, total_pages, results.map { it.toMovie() })
        }

        data class MovieDto(
                val vote_count: Int,
                val id: Int,
                val video: Boolean,
                val vote_average: Double,
                val title: String,
                val popularity: Double,
                val poster_path: String,
                val original_language: String,
                val original_title: String,
                val genre_ids: List<Int>,
                val backdrop_path: String,
                val adult: Boolean,
                val overview: String,
                val release_date: Date
        ) {
            fun toMovie(): Movie {
                return Movie(title, "horror", "100", "2008", poster_path)
            }
        }
    }

    data class GenreResultDto(val genres: List<GenreDto>) {

        fun toGenres() = genres.map { it.toGenre() }

        data class GenreDto(val id: Int, val name: String) {
            fun toGenre() = Genre(id, name)
        }
    }
}

