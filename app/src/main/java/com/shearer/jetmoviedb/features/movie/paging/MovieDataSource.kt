package com.shearer.jetmoviedb.features.movie.paging

import androidx.paging.PageKeyedDataSource
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import io.reactivex.disposables.CompositeDisposable

class MovieDataSource(private val movieInteractor: MovieInteractor, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Long, Movie>() {

    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Long>, callback: PageKeyedDataSource.LoadInitialCallback<Long, Movie>) {
//        compositeDisposable += movieInteractor.getPopular(1)
//                .applySchedulers()
//                .subscribe({
//                    callback.onResult(it.movies, null, 2L)
//                }, {
//                    val errorMessage = if (it == null) "unknown error" else it.message
//                    Log.e("MATT", "loadInitial - error: $errorMessage")
//                })
    }

    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, Movie>) {}

    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, Movie>) {
//        compositeDisposable += movieInteractor.getPopular(params.key)
//                .applySchedulers()
//                .subscribe({
//                    val nextKey = if (params.key == it.totalPages.toLong()) null else params.key + 1
//                    callback.onResult(it.movies, nextKey)
//
//                }, {
//                    val errorMessage = if (it == null) "unknown error" else it.message
//                    Log.e("MATT", "loadAfter - error: $errorMessage")
//                })
    }

}