package com.shearer.jetmoviedb.features.movie.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.getMovie
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbConstants
import com.shearer.jetmoviedb.shared.extensions.fadeIn
import com.shearer.jetmoviedb.shared.extensions.fadeOut
import com.shearer.jetmoviedb.shared.extensions.observeIt
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : AppCompatActivity() {

    private val model: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        intent.getMovie().run(::loadMovie)

        model.backgroundPoster.observeIt(this) {
            Picasso.get().load(MovieDbConstants.backgroundBaseUrl + it).fit().into(movieBackgroundImageView)
        }

        setupObservers()

    }

    private fun loadMovie(movie: Movie) {
        Picasso.get().load(MovieDbConstants.posterBaseUrl + movie.posterUrl).fit().noFade().into(moviePosterImageView)
        model.launchMovie(movie)
    }

    private fun setupObservers() {
        model.title.observeIt(this) {
            titleTextView.apply {
                text = it
                fadeIn()
                progressBar.fadeOut()
            }
        }

        model.overview.observeIt(this) {
            overviewTextView.apply {
                text = it
                fadeIn()
            }
        }

        model.runtime.observeIt(this) {
            runtimeTextView.apply {
                text = getString(R.string.runtime, it)
                fadeIn()
            }
        }

        model.revenue.observeIt(this) {
            revenueTextView.run {
                text = getString(R.string.revenue, it)
                fadeIn()
            }
        }

        model.language.observeIt(this) {
            languageTextView.run {
                text = getString(R.string.languages, it)
                fadeIn()
            }
        }

        model.link.observeIt(this) {
            linkTextView.run {
                text = it
                fadeIn()
            }
        }
    }

}
