package com.shearer.jetmoviedb

import com.google.gson.GsonBuilder
import com.shearer.jetmoviedb.features.movie.common.repository.dto.GenreResultDto
import com.shearer.jetmoviedb.features.movie.common.repository.dto.MovieDetailDto
import com.shearer.jetmoviedb.features.movie.common.repository.dto.MovieResultDto
import java.io.InputStreamReader

private const val RESOURCES_DIR = "/json/"

fun <T> readGson(file: String, type: Class<T>, dateFormat: String? = null): T {
    return InputStreamReader(Any::class.java.getResourceAsStream(RESOURCES_DIR + file)).use {
        GsonBuilder().setDateFormat(if (dateFormat.isNullOrEmpty()) "yyyy-MM-dd" else dateFormat).create().fromJson(it, type)
    }
}

fun createPopularMoviesDto() = readGson("movies_popular.json", MovieResultDto::class.java)

fun createGenreDto() = readGson("genres.json", GenreResultDto::class.java)

fun createMovieDetailsDto() = readGson("movie_details.json", MovieDetailDto::class.java)

fun createGenres() = createGenreDto().toGenres()

fun createPopularMovies() = createPopularMoviesDto().toMovies(createGenres())

fun createMovieDetails() = createMovieDetailsDto().toMovieDetails()

fun createMovie() = createPopularMovies().movies[0]