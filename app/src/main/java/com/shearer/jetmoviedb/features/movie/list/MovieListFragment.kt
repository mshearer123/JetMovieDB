package com.shearer.jetmoviedb.features.movie.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat.getTransitionName
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paginate.Paginate
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.getSearch
import com.shearer.jetmoviedb.features.movie.common.putMovie
import com.shearer.jetmoviedb.features.movie.detail.MovieDetailActivity
import com.shearer.jetmoviedb.shared.extensions.observeIt
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieListFragment : Fragment(), Paginate.Callbacks {

    private var isLoading = false
    private var hasCompleted = false
    private val model: MovieListViewModel by viewModel()

    private val movieAdapter by lazy {
        MovieListPagingAdapter(::launchDetails)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getSearch()?.let { model.loadSearchTerm(it) } ?: model.loadPopular()

        // added data observer to scroll to the top of the list if the data is deleted (refreshed)
        movieAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                if (positionStart == 0) {
                    recyclerView.layoutManager?.scrollToPosition(0)
                }
            }
        })

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
            setHasFixedSize(true)
        }
        Paginate.with(recyclerView, this).build()

        swipeToRefresh.setOnRefreshListener(model::refresh)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model.pagedListLiveData.observeIt(this, ::setPagedData)
        model.isLoading.observeIt(this) { isLoading = it }
        model.hasCompleted.observeIt(this) { hasCompleted = it }
        model.isRefreshing.observeIt(this) { swipeToRefresh.isRefreshing = it }
    }

    override fun onLoadMore() {
        if (!isLoading) {
            isLoading = true
            model.loadMore()
        }
    }

    override fun isLoading() = isLoading

    override fun hasLoadedAllItems() = hasCompleted

    private fun setPagedData(pagedList: PagedList<Movie>) {
        movieAdapter.submitList(pagedList)
    }

    private fun launchDetails(imageView: ImageView, movie: Movie) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java).apply {
            putMovie(movie)
        }
        getTransitionName(imageView)?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), imageView, it)
            startActivity(intent, options.toBundle())
        } ?: throw RuntimeException("transition name missing from imageView")
    }


}
