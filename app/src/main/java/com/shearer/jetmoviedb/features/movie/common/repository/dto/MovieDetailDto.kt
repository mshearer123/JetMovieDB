package com.shearer.jetmoviedb.features.movie.common.repository.dto

import com.shearer.jetmoviedb.features.movie.common.domain.MovieDetail
import com.shearer.jetmoviedb.shared.extensions.emptyIfNull


data class MovieDetailDto(
        val homepage: String?,
        val backdrop_path: String?,
        val overview: String?,
        val revenue: Int,
        val runtime: Int?,
        val spoken_languages: List<SpokenLanguageDto>
) {
    fun toMovieDetails(): MovieDetail {
        return MovieDetail(emptyIfNull(homepage),
                emptyIfNull(overview),
                emptyIfNull(backdrop_path),
                revenue,
                runtime?.let { it } ?: 0,
                spoken_languages.map(SpokenLanguageDto::name).toList()
        )
    }
}