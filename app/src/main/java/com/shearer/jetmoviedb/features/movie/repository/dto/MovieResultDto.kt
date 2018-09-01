package com.shearer.jetmoviedb.features.movie.repository.dto

import com.shearer.jetmoviedb.features.movie.domain.MovieResults

data class MovieResultDto(
        val page: Int,
        val total_results: Int,
        val total_pages: Int,
        val results: List<MovieDto>
) {

    fun toMovies(genres: Map<Int, String>): MovieResults {
        return MovieResults(page, total_results, total_pages, results.map { it.toMovie(genres) })
    }

}