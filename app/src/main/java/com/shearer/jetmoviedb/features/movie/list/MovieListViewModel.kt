package com.shearer.jetmoviedb.features.movie.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor
import com.shearer.jetmoviedb.shared.extensions.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MovieListViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {

    private var listConfig: ListConfig = PopularConfig()
    private val pagedListConfig = PagedList.Config.Builder()
            .setPrefetchDistance(5)
            .setPageSize(20)
            .build()


    lateinit var pagedListLiveData: LiveData<PagedList<Movie>>

    var isLoading = MutableLiveData<Boolean>()
    var hasCompleted = MutableLiveData<Boolean>()
    var isRefreshing = MutableLiveData<Boolean>()

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun loadSearchTerm(searchString: String) {
        listConfig = SearchConfig(searchString)
        val dataSourceFactory = movieInteractor.getDataSource(listConfig)
        pagedListLiveData = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }

    fun loadPopular() {
        val dataSourceFactory = movieInteractor.getDataSource(listConfig)
        pagedListLiveData = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
    }

    fun loadMore() {
        compositeDisposable += movieInteractor.getMovies(listConfig)
                .applySchedulers()
                .subscribe(::onLoadMoreSuccess, ::onLoadMoreError)
    }

    private fun onLoadMoreSuccess(movieResults: MovieResults) {
        if (movieResults.page == movieResults.totalPages) {
            hasCompleted.value = true
        }
        isLoading.value = false
    }

    private fun onLoadMoreError(throwable: Throwable) {
        Log.e("loadMore", "Error loading movies: " + throwable.message)
        isLoading.value = false
    }

    fun refresh() {
        isRefreshing.value = true
        compositeDisposable += movieInteractor.deleteMovieCache(listConfig)
                .applySchedulers()
                .subscribe(::onRefreshSuccess, ::onRefreshError)
    }

    private fun onRefreshSuccess() {
        isRefreshing.value = false
    }

    private fun onRefreshError(throwable: Throwable) {
        Log.e("refresh", "Error deleting movie cache: " + throwable.message)
        isRefreshing.value = false
    }
}
