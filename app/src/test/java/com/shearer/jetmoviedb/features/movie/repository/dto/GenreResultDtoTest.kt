package com.shearer.jetmoviedb.features.movie.repository.dto

import com.google.common.truth.Truth.assertThat
import com.shearer.jetmoviedb.createGenreDto
import org.junit.Test

class GenreResultDtoTest {

    @Test
    fun toGenres_convertsGenresToHashMap() {
        createGenreDto().toGenres().run {
            assertThat(size).isEqualTo(19)
            assertThat(get(28)).isEqualTo("Action")
            assertThat(get(12)).isEqualTo("Adventure")
        }
    }
}