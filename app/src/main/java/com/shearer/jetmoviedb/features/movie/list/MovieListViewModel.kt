package com.shearer.jetmoviedb.features.movie.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import com.shearer.jetmoviedb.shared.extensions.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MovieListViewModel(private val movieInteractor: MovieInteractor) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val movies = MutableLiveData<MutableList<MovieListItem>>().apply { value = mutableListOf() }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    init {
        compositeDisposable += movieInteractor
                .getPopular()
                .map(::transform)
                .applySchedulers()
                .subscribe(::onGetPopularMoviesSuccess, ::onGetPopularMoviesFailure)
    }


    public fun stuff() {

    }

    private fun transform(movieResults: MovieResults): List<MovieListItem> {

        return movieResults.movies.map {
            MovieListItem(title = "${it.title} (${it.releaseYear})",
                    genres = it.genres,
                    popularity = it.popularity,
                    photoUrl = it.thumbnailUrl)
        }
    }

    public fun onMovieClicked(index: Int) {

    }

    private fun onGetPopularMoviesSuccess(movieResults: List<MovieListItem>) {
        movies.postValue(movieResults.toMutableList())
    }

    private fun onGetPopularMoviesFailure(throwable: Throwable) {
        Log.e("MATT", "throwable: " + throwable.message)
    }

}
