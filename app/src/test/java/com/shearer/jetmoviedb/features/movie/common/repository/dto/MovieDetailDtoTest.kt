package com.shearer.jetmoviedb.features.movie.common.repository.dto

import com.google.common.truth.Truth.assertThat
import com.shearer.jetmoviedb.createMovieDetailsDto
import org.junit.Test

class MovieDetailDtoTest {

    @Test fun toMovieDetails_convertsToMovieDetails() {
        createMovieDetailsDto().toMovieDetails().run {
            assertThat(homePage).isEqualTo("https://www.foxmovies.com/movies/deadpool-2")
            assertThat(overview).isEqualTo("Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.")
            assertThat(backdropPath).isEqualTo("/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg")
            assertThat(revenue).isEqualTo(732419226)
            assertThat(runtime).isEqualTo(121)
            assertThat(languages).isEqualTo(listOf("English"))
        }
    }
}