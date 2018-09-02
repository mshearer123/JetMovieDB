package com.shearer.jetmoviedb.features.movie.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val searchTerm = MutableLiveData<SearchInfo>()
    private val result = map(searchTerm) {
        movieInteractor.getMovies(it, compositeDisposable)
    }

    val movies = switchMap(result) { it.pagedList }!!

    init {
        searchTerm.value = SearchInfo(SearchInfo.Type.POPULAR)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun onRefresh() {

    }

    fun onMovieClicked(index: Int) {

    }

    fun onPopularClicked() {
        searchTerm.value = SearchInfo(SearchInfo.Type.POPULAR)
    }

    fun onSearchClicked(searchString: String) {
        searchTerm.value = SearchInfo(SearchInfo.Type.SEARCH, searchString)
    }

}
