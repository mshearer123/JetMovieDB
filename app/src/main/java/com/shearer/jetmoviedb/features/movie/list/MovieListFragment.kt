package com.shearer.jetmoviedb.features.movie.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shearer.jetmoviedb.R
import org.koin.android.ext.android.inject

class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.stuff()
    }

    companion object {
        fun newInstance() = MovieListFragment()
    }

}
