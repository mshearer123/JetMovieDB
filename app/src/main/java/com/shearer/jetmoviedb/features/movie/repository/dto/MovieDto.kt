package com.shearer.jetmoviedb.features.movie.repository.dto

import com.shearer.jetmoviedb.features.movie.domain.Movie

data class MovieDto(
        val id: Int,
        val title: String,
        val popularity: Double,
        val poster_path: String?,
        val genre_ids: List<Int>,
        val backdrop_path: String?,
        val release_date: String
) {
    fun toMovie(genres: Map<Int, String>, type: String): Movie {
        return Movie(title, resolveGenres(genres, genre_ids), popularity.toString(), release_date.split("-")[0], poster_path, type, id)
    }

    private fun resolveGenres(genres: Map<Int, String>, genreIds: List<Int>): String {
        return if (genres.isEmpty()) {
            ""
        } else {
            genreIds.map { genres[it] }.joinToString()
        }
    }
}