package com.shearer.jetmoviedb.features.movie.list

import androidx.lifecycle.ViewModel
import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import com.shearer.jetmoviedb.shared.extensions.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MovieListViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    init {
        compositeDisposable += movieInteractor
                .getPopular()
                .applySchedulers()
                .subscribe(this::onGetPopularMoviesSuccess, this::onGetPopularMoviesFailure)
    }


    public fun stuff() {

    }

    private fun onGetPopularMoviesSuccess(movieResults: MovieResults) {
        for (movie in movieResults.movies) {
        }
    }

    private fun onGetPopularMoviesFailure(throwable: Throwable) {

    }
}
