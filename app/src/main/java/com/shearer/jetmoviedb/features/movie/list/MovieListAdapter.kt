package com.shearer.jetmoviedb.features.movie.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.shared.extensions.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_movie.view.*

class MovieListAdapter(var data: List<MovieListItem> = emptyList(),
                       private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieViewHolder(parent.inflate(R.layout.list_item_movie))

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(data[position], itemClickListener)

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView = itemView.movieTitle
        private val genreTextView = itemView.movieGenre
        private val populatityTextView = itemView.moviePopularity
        private val posterImageView = itemView.moviePoster

        fun bind(item: MovieListItem, itemClickListener: (Int) -> Unit) {
            titleTextView.text = item.title
            genreTextView.text = item.genres
            populatityTextView.text = item.popularity
            Picasso.get().load(item.photoUrl).fit().into(posterImageView)
            itemView.setOnClickListener {
                itemClickListener(adapterPosition)
            }
        }
    }
}