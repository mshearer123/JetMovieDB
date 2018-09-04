package com.shearer.jetmoviedb.features.movie.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.shearer.jetmoviedb.Rx2SchedulersOverrideRule
import com.shearer.jetmoviedb.createMovie
import com.shearer.jetmoviedb.createMovieDetails
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test

class MovieDetailViewModelTest {

    @Rule @JvmField var rule = InstantTaskExecutorRule()
    @Rule @JvmField var rxRule = Rx2SchedulersOverrideRule()

    private val interactor = mock<MovieInteractor> {
        on { getMovieDetails(any()) } doReturn Single.just(createMovieDetails())

    }
    private val model by lazy { MovieDetailViewModel(interactor) }


    @Test fun launchMovie_callsInteractor() {
        val movie = createMovie()
        model.launchMovie(movie)

        verify(interactor).getMovieDetails(299536)
    }

    @Test fun launchMovie_getDetails_verifyValues() {
        val movie = createMovie()

        model.run {
            launchMovie(movie)
            assertThat(backgroundPoster.value).isEqualTo("/3P52oz9HPQWxcwHOwxtyrVV1LKi.jpg")
            assertThat(title.value).isEqualTo("Avengers: Infinity War")
            assertThat(overview.value).isEqualTo("Wisecracking mercenary Deadpool battles the evil and powerful Cable and other bad guys to save a boy's life.")
            assertThat(revenue.value).isEqualTo("732,419,226")
            assertThat(runtime.value).isEqualTo("121")
            assertThat(language.value).isEqualTo("English")
            assertThat(link.value).isEqualTo("https://www.foxmovies.com/movies/deadpool-2")
        }
    }
}