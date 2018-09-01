package com.shearer.jetmoviedb.features.movie.paging


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import io.reactivex.disposables.CompositeDisposable

class MovieDataFactory(movieInteractor: MovieInteractor, compositeDisposable: CompositeDisposable
) : DataSource.Factory<Long, Movie>() {

    private val movieDataSource = MovieDataSource(movieInteractor, compositeDisposable)
    private val mutableLiveData: MutableLiveData<MovieDataSource> = MutableLiveData()

    override fun create(): DataSource<Long, Movie> {
        mutableLiveData.postValue(movieDataSource)
        return movieDataSource
    }
}