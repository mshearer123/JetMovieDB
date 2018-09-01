package com.shearer.jetmoviedb.features.movie.repository.dto

import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.shared.extensions.resolveYear
import java.util.*

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
    fun toMovie(genres: Map<Int, String>): Movie {
        return Movie(title, resolveGenres(genres, genre_ids), popularity.toString(), release_date.resolveYear(), poster_path)
    }

    private fun resolveGenres(genres: Map<Int, String>, genreIds: List<Int>): String {
        return genreIds.map { genres[it] }.joinToString()
    }
}