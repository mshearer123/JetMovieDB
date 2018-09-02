package com.shearer.jetmoviedb.features.movie.paging

import android.arch.paging.PagingRequestHelper
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.list.SearchInfo
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import com.shearer.jetmoviedb.shared.extensions.applySchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import java.util.concurrent.Executors

class MovieBoundaryCallback(private val disposables: CompositeDisposable,
                            private val movieRepository: MovieRepository,
                            private val searchInfo: SearchInfo,
                            private val handleResponse: (MovieResults) -> Unit
) : PagedList.BoundaryCallback<Movie>() {

    private val ioExecutor = Executors.newSingleThreadExecutor()
    private val helper = PagingRequestHelper(ioExecutor)

    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { callback ->
            when (searchInfo.type) {
                SearchInfo.Type.POPULAR -> {
                    disposables += movieRepository.getPopular(1)
                            .applySchedulers()
                            .subscribe({
                                handleSuccess(it, callback)
                            }, callback::recordFailure)
                }
                SearchInfo.Type.SEARCH -> {
                    disposables += movieRepository.getMoviesBySearchTerm(1, searchInfo.term!!)
                            .applySchedulers()
                            .subscribe({
                                handleSuccess(it, callback)
                            }, callback::recordFailure)
                }
            }
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {
        val nextPage = itemAtEnd.page + 1
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { callback ->
            when (searchInfo.type) {
                SearchInfo.Type.POPULAR -> {
                    disposables += movieRepository.getPopular(nextPage.toLong())
                            .applySchedulers()
                            .subscribe({
                                handleSuccess(it, callback)
                            }, callback::recordFailure)
                }
                SearchInfo.Type.SEARCH -> {
                    disposables += movieRepository.getMoviesBySearchTerm(nextPage.toLong(), searchInfo.term!!)
                            .applySchedulers()
                            .subscribe({
                                handleSuccess(it, callback)
                            }, callback::recordFailure)
                }
            }
        }
    }

    private fun handleSuccess(movieResults: MovieResults, callback: PagingRequestHelper.Request.Callback) {
        ioExecutor.execute {
            handleResponse(movieResults)
            callback.recordSuccess()
        }
    }
}