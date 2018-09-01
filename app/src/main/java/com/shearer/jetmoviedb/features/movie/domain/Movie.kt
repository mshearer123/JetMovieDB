package com.shearer.jetmoviedb.features.movie.domain

data class Movie(val title: String,
                 val genres: String,
                 val popularity: String,
                 val releaseYear: String,
                 val thumbnailUrl: String)
