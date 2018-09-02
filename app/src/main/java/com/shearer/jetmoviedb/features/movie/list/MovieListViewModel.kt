package com.shearer.jetmoviedb.features.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var moviesLiveData: LiveData<PagedList<Movie>>

    init {
        moviesLiveData = movieInteractor.getPopular(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onRefresh() {
        moviesLiveData = movieInteractor.getPopular(compositeDisposable, refresh = true)
    }

    fun onMovieClicked(index: Int) {

    }
}
