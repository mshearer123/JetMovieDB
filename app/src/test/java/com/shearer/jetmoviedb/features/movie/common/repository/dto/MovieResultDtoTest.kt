package com.shearer.jetmoviedb.features.movie.common.repository.dto

import com.google.common.truth.Truth.assertThat
import com.shearer.jetmoviedb.createGenreDto
import com.shearer.jetmoviedb.createPopularMoviesDto
import org.junit.Test

class MovieResultDtoTest {

    private val type = "type"
    private val genres = createGenreDto().toGenres()
    private val movieResultDto = createPopularMoviesDto()

    @Test
    fun toMovies_convertsDtoToDomain() {
        movieResultDto.toMovies(genres, type).run {
            assertThat(page).isEqualTo(1)
            assertThat(totalResults).isEqualTo(19831)
            assertThat(totalPages).isEqualTo(992)
            assertThat(movies.size).isEqualTo(20)
        }
    }

}