package com.shearer.jetmoviedb.features.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbRepository
import com.shearer.jetmoviedb.shared.extensions.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MovieListViewModel(private val movieInteractor: MovieInteractor, private val movieRepository: MovieDbRepository) : ViewModel() {

    var searchInfo = SearchInfo(SearchInfo.Type.POPULAR)
    private val config = PagedList.Config.Builder()
            .setPageSize(20)
            .build()


    lateinit var pagedListLiveData: LiveData<PagedList<Movie>>

    var isLoading = MutableLiveData<Boolean>()
    var hasCompleted = MutableLiveData<Boolean>()

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun searchTerm(searchString: String) {
        searchInfo = SearchInfo(SearchInfo.Type.SEARCH, searchString)
        val dataSourceFactory = movieRepository.getMovieDataSource(searchInfo)
        pagedListLiveData = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun popular() {
        val dataSourceFactory = movieRepository.getMovieDataSource(searchInfo)
        pagedListLiveData = LivePagedListBuilder(dataSourceFactory, config).build()

    }

    fun loadMore() {
        compositeDisposable += movieInteractor.getMovies(searchInfo)
                .doOnSuccess { movieRepository.insertMoviesInDatabase(searchInfo, it) }
                .applySchedulers()
                .subscribe({
                    if (it.page == it.totalPages) {
                        hasCompleted.value = true
                    }
                    isLoading.value = false
                }, {
                    isLoading.value = false
                })
    }


    fun onRefresh() {

    }
}
