package com.shearer.jetmoviedb.features.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.shared.extensions.observeNotNull
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment() {

    private val movieListViewModel: MovieListViewModel by viewModel()

    private val movieAdapter by lazy { MovieListPagingAdapter(movieListViewModel::onMovieClicked) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
            setHasFixedSize(true)
        }

        swipeToRefreshLayout.setOnRefreshListener(movieListViewModel::onRefresh)
        searchFAB.setOnClickListener { movieListViewModel.onSearchClicked() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieListViewModel.movies.observeNotNull(this) { movieAdapter.submitList(it) }
        movieListViewModel.refreshing.observeNotNull(this) { swipeToRefreshLayout.isRefreshing = it }
    }
}
