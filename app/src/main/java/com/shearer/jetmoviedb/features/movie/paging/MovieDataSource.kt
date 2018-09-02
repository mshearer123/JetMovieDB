package com.shearer.jetmoviedb.features.movie.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import com.shearer.jetmoviedb.shared.extensions.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

class MovieDataSource(private val searchTerm: String,
                      private val movieRepository: MovieRepository,
                      private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Long, Movie>() {

    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Long>, callback: PageKeyedDataSource.LoadInitialCallback<Long, Movie>) {
        compositeDisposable += movieRepository.getMoviesBySearchTerm(1, searchTerm)
                .applySchedulers()
                .subscribe({
                    callback.onResult(it.movies, null, 2L)
                }, {
                    val errorMessage = if (it == null) "unknown error" else it.message
                    Log.e("MATT", "loadInitial - error: $errorMessage")
                })
    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, Movie>) {}

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, Movie>) {
        compositeDisposable += movieRepository.getMoviesBySearchTerm(params.key, searchTerm)
                .applySchedulers()
                .subscribe({
                    val nextKey = if (params.key == it.totalPages.toLong()) null else params.key + 1
                    callback.onResult(it.movies, nextKey)

                }, {
                    val errorMessage = if (it == null) "unknown error" else it.message
                    Log.e("MATT", "loadAfter - error: $errorMessage")
                })
    }

}