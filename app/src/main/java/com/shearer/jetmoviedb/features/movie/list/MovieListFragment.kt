package com.shearer.jetmoviedb.features.movie.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
        searchFAB.setOnClickListener {
            editText.visibility = View.VISIBLE
            editText.requestFocus()
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, 0)
        }

        editText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                movieListViewModel.onSearchClicked(textView.text.toString())
                recyclerView.scrollToPosition(0)
                editText.setText("")
                editText.visibility = View.GONE
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
                popularChip.visibility = View.VISIBLE
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }

        popularChip.setOnClickListener {
            popularChip.visibility = View.GONE
            movieListViewModel.onPopularClicked()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieListViewModel.movies.observeNotNull(this) { movieAdapter.submitList(it) }
    }
}
