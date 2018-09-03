package com.shearer.jetmoviedb.features.movie.detail

import androidx.lifecycle.ViewModel
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor
import io.reactivex.disposables.CompositeDisposable

class MovieDetailViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()


    init {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}
