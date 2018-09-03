package com.shearer.jetmoviedb.features.movie.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.shared.extensions.observeNotNull
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.layout_movie_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieDetailActivity : AppCompatActivity() {

    private val movieDetailsViewModel: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val movie = intent.getSerializableExtra(Movie.TAG) as Movie
        movieDetailsViewModel.launchMovie(movie)
        Picasso.get().load("https://image.tmdb.org/t/p/w342/" + movie.url).fit().noFade().into(moviePosterImageView)

        movieDetailsViewModel.backgroundPoster.observeNotNull(this) {
            Picasso.get().load("https://image.tmdb.org/t/p/w780/" + it).fit().into(movieBackgroundImageView)
        }

        overviewTextView.text = "efoewfoefwenflkewfnlknf"
    }

}
