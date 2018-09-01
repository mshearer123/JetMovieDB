package com.shearer.jetmoviedb.features.movie.domain

import androidx.recyclerview.widget.DiffUtil

data class Movie(val title: String,
                 val genres: String,
                 val popularity: String,
                 val releaseYear: String,
                 val thumbnailUrl: String) {

    companion object {
        val MOVIE_DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}
