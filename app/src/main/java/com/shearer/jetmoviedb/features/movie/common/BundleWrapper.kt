package com.shearer.jetmoviedb.features.movie.common

import android.content.Intent
import com.shearer.jetmoviedb.features.movie.common.domain.Movie

fun Intent.putMovie(movie: Movie): Intent = putExtra("movie", movie)

fun Intent.getMovie() = getSerializableExtra("movie") as Movie