package com.shearer.jetmoviedb.features.movie.list

import android.util.Log
import androidx.lifecycle.ViewModel
import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import com.shearer.jetmoviedb.shared.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MovieListViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        compositeDisposable += movieInteractor
                .getPopular()
                .applySchedulers()
                .subscribe(this::onGetPopularMoviesSuccess, this::onGetPopularMoviesFailure)
    }


    public fun stuff() {

    }

    private fun onGetPopularMoviesSuccess(movieResuls: MovieResults) {
        for (movie in movieResuls.movies) {
            Log.d("MATT", "movie: " + movie.title)
        }
    }

    private fun onGetPopularMoviesFailure(throwable: Throwable) {
        Log.e("MATT", "throwable: " + throwable.message)
    }
}
