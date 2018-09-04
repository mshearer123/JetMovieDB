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
import java.text.NumberFormat

class MovieDetailViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {

    lateinit var movie: Movie
    private val compositeDisposable = CompositeDisposable()
    var backgroundPoster = MutableLiveData<String>()
    var title = MutableLiveData<String>()
    var overview = MutableLiveData<String>()
    var revenue = MutableLiveData<String>()
    var runtime = MutableLiveData<String>()
    var language = MutableLiveData<String>()
    var link = MutableLiveData<String>()

    fun launchMovie(movie: Movie) {
        this.movie = movie
        compositeDisposable += movieInteractor.getMovieDetails(movie.id)
                .applySchedulers()
                .subscribe(::movieDetailSuccess, ::movieDetailError)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun movieDetailSuccess(movieDetail: MovieDetail) {
        backgroundPoster.value = movieDetail.backdropPath
        title.value = movie.title
        overview.value = movieDetail.overview
        val revenueFormatted = NumberFormat.getIntegerInstance().format(movieDetail.revenue)
        revenue.value = revenueFormatted
        runtime.value = movieDetail.runtime.toString()
        language.value = movieDetail.languages.joinToString { it }
        link.value = movieDetail.homePage
    }

    private fun movieDetailError(throwable: Throwable) {
        Log.e("refresh", "Error deleting movie cache: " + throwable.message)
    }

}
