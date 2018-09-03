package com.shearer.jetmoviedb.features.movie.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.domain.MovieDetail
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor
import com.shearer.jetmoviedb.shared.extensions.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MovieDetailViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {

    lateinit var movie: Movie
    private val compositeDisposable = CompositeDisposable()
    var backgroundPoster = MutableLiveData<String>()
    var poster = MutableLiveData<String>()
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
        poster.value = movie.url
        title.value = movie.title
        overview.value = movieDetail.overview
        revenue.value = movieDetail.revenue.toString()
        runtime.value = movieDetail.runtime.toString()
        language.value = movieDetail.languages.joinToString { it }
        link.value = movieDetail.homePage
    }

    private fun movieDetailError(throwable: Throwable) {
    }

}
