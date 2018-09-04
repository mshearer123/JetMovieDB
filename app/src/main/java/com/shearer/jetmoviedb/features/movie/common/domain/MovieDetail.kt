package com.shearer.jetmoviedb.features.movie.common.domain

data class MovieDetail(
        val homePage: String,
        val overview: String,
        val backdropPath: String,
        val revenue: Int,
        val runtime: Int,
        val languages: List<String>)