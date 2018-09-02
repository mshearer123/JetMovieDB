package com.shearer.jetmoviedb.features.movie.repository

import android.arch.paging.PagingRequestHelper
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.repository.dto.MovieResultDto
import com.shearer.jetmoviedb.shared.extensions.applySchedulers
import java.util.concurrent.Executor

class MovieBoundaryCallback(private val dao: MovieDbApi.Dao,
                            private val ioExecutor: Executor,
                            private val handleResponse: (List<Movie>) -> Unit
                            ) : PagedList.BoundaryCallback<Movie>() {

    private val helper = PagingRequestHelper(ioExecutor)

    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { callback ->
            val disposable = dao.getPopularMovies(1).applySchedulers()
                    .subscribe({
                        handleSuccess(it, callback)
                    }, callback::recordFailure)
        }
    }

    private fun handleSuccess(movieResultDto: MovieResultDto, callback: PagingRequestHelper.Request.Callback) {
        ioExecutor.execute {
            handleResponse(movieResultDto.toMovies(emptyMap()).movies)
            callback.recordSuccess()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Movie) {

        val nextPage = (itemAtEnd.indexInResponse + 1) / 20 + 1

        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { callback ->
            val disposable = dao.getPopularMovies(nextPage.toLong()).applySchedulers()
                    .subscribe({
                        handleSuccess(it, callback)
                    }, callback::recordFailure)
        }
    }

}