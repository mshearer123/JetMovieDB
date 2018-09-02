package com.shearer.jetmoviedb.features.movie.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val searchTerm = MutableLiveData<SearchInfo>()
    private val result = map(searchTerm) {
        movieInteractor.getMovies(it, compositeDisposable)
    }

    val movies = switchMap(result) { it.pagedList }!!
    val refreshing = switchMap(result) { it.refreshing }!!


    init {
        searchTerm.value = SearchInfo(SearchInfo.Type.POPULAR)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onRefresh() {
        searchTerm.value = SearchInfo(SearchInfo.Type.SEARCH, "king kong")
    }

    fun onMovieClicked(index: Int) {

    }

    fun onSearchClicked() {

    }

    fun onSearch(searchTerm: String) {
//        moviesLiveData = movieInteractor.getMovies(compositeDisposable, refresh = true, searchTerm = searchTerm)
    }
}
