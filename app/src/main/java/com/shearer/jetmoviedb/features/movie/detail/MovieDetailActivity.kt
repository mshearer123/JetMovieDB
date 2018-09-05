package com.shearer.jetmoviedb.features.movie.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.getMovie
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbConstants
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
        model.title.observeIt(this) { titleTextView.text = it }
        model.overview.observeIt(this) { overviewTextView.text = it }
        model.runtime.observeIt(this) { runtimeTextView.text = getString(R.string.runtime, it) }
        model.revenue.observeIt(this) { revenueTextView.text = getString(R.string.revenue, it) }
        model.language.observeIt(this) { languageTextView.text = getString(R.string.languages, it) }
        model.link.observeIt(this) { linkTextView.text = it }
    }

    private fun loadMovie(movie: Movie) {
        Picasso.get().load(MovieDbConstants.posterBaseUrl + movie.posterUrl).fit().noFade().into(moviePosterImageView)
        model.launchMovie(movie)
    }

}
