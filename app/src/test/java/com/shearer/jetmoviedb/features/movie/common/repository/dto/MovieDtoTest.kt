package com.shearer.jetmoviedb.features.movie.common.repository.dto

import com.google.common.truth.Truth.assertThat
import com.shearer.jetmoviedb.createGenreDto
import com.shearer.jetmoviedb.createPopularMoviesDto
import org.junit.Test

class MovieDtoTest {

    private val genres = createGenreDto().toGenres()
    private val movie = createPopularMoviesDto().results[0].toMovie(genres)


    @Test
    fun toMovie_convertsToMovie_resolvesTitle() {
        assertThat(movie.title).isEqualTo("Avengers: Infinity War")
    }

    @Test
    fun toMovie_convertsToMovie_resolvesGenres() {
        assertThat(movie.genres).isEqualTo("Adventure, Science Fiction, Fantasy, Action")
    }

    @Test
    fun toMovie_convertsToMovie_resolvesPopularity() {
        assertThat(movie.popularity).isEqualTo("220.311")
    }

    @Test
    fun toMovie_convertsToMovie_resolvesReleaseYear() {
        assertThat(movie.releaseYear).isEqualTo("2018")
    }

    @Test
    fun toMovie_convertsToMovie_resolvesPosterPath() {
        assertThat(movie.posterUrl).isEqualTo("/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg")
    }
}