package com.shearer.jetmoviedb.features.movie.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.domain.MovieDetail
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor
import com.shearer.jetmoviedb.shared.extensions.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MovieDetailViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    var backgroundPoster = MutableLiveData<String>()

    init {

    }

    fun launchMovie(movie: Movie) {
        compositeDisposable += movieInteractor.getMovieDetails(movie.id)
                .applySchedulers()
                .subscribe(::onMovieDetailSuccess, ::onMOvieDetailFailre)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun onMovieDetailSuccess(movieDetail: MovieDetail) {
        backgroundPoster.value = movieDetail.backdropPath
        Log.e("MATT", "movie: " + movieDetail.toString())
    }

    private fun onMOvieDetailFailre(throwable: Throwable) {
        Log.e("MATT", "error: $throwable")
    }

}
