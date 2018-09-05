package com.shearer.jetmoviedb.features.movie.common

import android.content.Intent
import android.os.Bundle
import com.shearer.jetmoviedb.features.movie.common.domain.Movie


fun Intent.putMovie(movie: Movie): Intent = putExtra("movie", movie)

fun Intent.getMovie() = getSerializableExtra("movie") as Movie

fun Bundle.putSearch(searchTerm: String): Bundle {
    putString("search", searchTerm)
    return this
}
fun Bundle.getSearch() = getString("search") as String