package com.shearer.jetmoviedb.features.movie.common.repository.dto

data class GenreResultDto(val genres: List<GenreDto>
) {
    fun toGenres() = genres.associateBy(GenreDto::id, GenreDto::name)
}