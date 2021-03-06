package com.shearer.jetmoviedb.features.movie.list

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbConstants
import com.shearer.jetmoviedb.shared.extensions.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieListPagingAdapter(private val itemClickListener: (ImageView, Movie) -> Unit
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

        fun bind(movie: Movie, itemClickListener: (ImageView, Movie) -> Unit) {
            titleTextView.text = itemView.context.getString(R.string.movie_title, movie.title, movie.releaseYear)
            genreTextView.text = movie.genres
            popularityTextView.text = movie.popularity
            Picasso.get().load(MovieDbConstants.posterBaseUrl + movie.posterUrl).fit().into(posterImageView)
            itemView.setOnClickListener {
                itemClickListener(posterImageView, movie)
            }
        }
    }

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
