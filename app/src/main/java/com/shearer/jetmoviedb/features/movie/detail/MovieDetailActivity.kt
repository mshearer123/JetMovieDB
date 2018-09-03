package com.shearer.jetmoviedb.features.movie.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : AppCompatActivity() {

    private val model: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val movie = intent.getSerializableExtra(Movie.TAG) as Movie
        Picasso.get().load("https://image.tmdb.org/t/p/w342/" + movie.posterUrl).fit().noFade().into(moviePosterImageView)
        model.launchMovie(movie)

        model.backgroundPoster.observe(this, Observer {
            Picasso.get().load("https://image.tmdb.org/t/p/w780/" + it).fit().into(movieBackgroundImageView)
        })

        model.title.observe(this, Observer { titleTextView.text = it })
        model.overview.observe(this, Observer { overviewTextView.text = it })
        model.runtime.observe(this, Observer { runtimeTextView.text = it })
        model.revenue.observe(this, Observer { revenueTextView.text = it })
        model.language.observe(this, Observer { languageTextView.text = it })
        model.link.observe(this, Observer { linkTextView.text = it })
    }

}
