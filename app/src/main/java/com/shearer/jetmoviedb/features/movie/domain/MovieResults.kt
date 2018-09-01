package com.shearer.jetmoviedb.features.movie.domain

data class MovieResults(val page: Int, val totalResults: Int, val totalPages: Int, val movies: List<Movie>)
