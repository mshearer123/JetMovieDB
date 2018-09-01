package com.shearer.jetmoviedb.features.movie.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import com.shearer.jetmoviedb.features.movie.paging.MovieDataFactory
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel(movieInteractor: MovieInteractor) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val popularMovieDataFactory = MovieDataFactory(movieInteractor, compositeDisposable)
    var moviesLiveData: LiveData<PagedList<Movie>>

    init {
        val pagingConfig = PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build()

        moviesLiveData = LivePagedListBuilder(popularMovieDataFactory, pagingConfig).build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


    public fun onMovieClicked(index: Int) {

    }


}
