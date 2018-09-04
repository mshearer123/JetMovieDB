package com.shearer.jetmoviedb.features.movie.common.repository.dto

import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults

data class MovieResultDto(
        val page: Int,
        val total_results: Int,
        val total_pages: Int,
        val results: List<MovieDto>
) {

    fun toMovies(genres: Map<Int, String>): MovieResults {
        val movies = results.map { it.toMovie(genres) }
        return MovieResults(page, total_results, total_pages, movies)
    }
}