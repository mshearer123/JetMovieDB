package com.shearer.jetmoviedb.features.movie.list

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.domain.Movie.Companion.MOVIE_DIFF_CALLBACK
import com.shearer.jetmoviedb.shared.extensions.getString
import com.shearer.jetmoviedb.shared.extensions.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieListPagingAdapter(private val itemClickListener: (Int) -> Unit
) : PagedListAdapter<Movie, MovieListPagingAdapter.MovieViewHolder>(MOVIE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(parent.inflate(R.layout.list_item_movie))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, itemClickListener) }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView = itemView.movieTitle
        private val genreTextView = itemView.movieGenre
        private val popularityTextView = itemView.moviePopularity
        private val posterImageView = itemView.moviePoster

        fun bind(movie: Movie, itemClickListener: (Int) -> Unit) {
            titleTextView.text = getString(R.string.movie_title, movie.title, movie.releaseYear)
            genreTextView.text = movie.genres
            popularityTextView.text = movie.popularity
            Picasso.get().load("https://image.tmdb.org/t/p/w342/" + movie.thumbnailUrl).fit().into(posterImageView)
            itemView.setOnClickListener {
                itemClickListener(adapterPosition)
            }
        }
    }
}
