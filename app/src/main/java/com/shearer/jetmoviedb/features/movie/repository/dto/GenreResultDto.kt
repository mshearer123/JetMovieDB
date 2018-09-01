package com.shearer.jetmoviedb.features.movie.repository.dto

data class GenreResultDto(val genres: List<GenreDto>) {

    fun toGenres() = genres.associateBy({ it.id }, { it.name })

}