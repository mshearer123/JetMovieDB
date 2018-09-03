package com.shearer.jetmoviedb.features.movie.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat.getTransitionName
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.detail.MovieDetailActivity
import com.shearer.jetmoviedb.shared.extensions.observeNotNull
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MovieListFragment : Fragment() {

    private val movieListViewModel: MovieListViewModel by viewModel()

    private val movieAdapter by lazy {
        MovieListPagingAdapter(::launchDetails)
    }

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

        editText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                movieListViewModel.onSearchClicked(textView.text.toString())
                recyclerView.scrollToPosition(0)
                editText.clearFocus()
                hideKeyboard()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieListViewModel.movies.observeNotNull(this) { movieAdapter.submitList(it) }
        movieListViewModel.launchDetail.observeNotNull(this) {

        }
    }

    private fun launchDetails(imageView: ImageView, movie: Movie) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java).apply {
            putExtra(Movie.TAG, movie)
        }
        getTransitionName(imageView)?.let {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), imageView, it)
            startActivity(intent, options.toBundle())
        } ?: throw RuntimeException("transition name missing from imageView")
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}
