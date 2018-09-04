package com.shearer.jetmoviedb.features.movie.common.repository.dto

import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.shared.extensions.emptyIfNull

data class MovieDto(
        val id: Int,
        val title: String,
        val popularity: Double,
        val poster_path: String?,
        val genre_ids: List<Int>,
        val backdrop_path: String?,
        val release_date: String
) {
    fun toMovie(genres: Map<Int, String>): Movie {
        return Movie(title = title,
                genres = resolveGenres(genres, genre_ids),
                popularity = popularity.toString(),
                releaseYear = release_date.split("-")[0],
                posterUrl = emptyIfNull(poster_path),
                movieId = id)
    }

    private fun resolveGenres(genres: Map<Int, String>, genreIds: List<Int>): String {
        return if (genres.isEmpty()) {
            ""
        } else {
            genreIds.map { genres[it] }.joinToString()
        }
    }
}