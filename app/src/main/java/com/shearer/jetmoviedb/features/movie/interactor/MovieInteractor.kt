package com.shearer.jetmoviedb.features.movie.interactor

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.domain.Movie
import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import io.reactivex.Single

interface MovieInteractor {
    fun getPopular(): LiveData<PagedList<Movie>>
}

class MovieInteractorDefault(private val movieRepository: MovieRepository) : MovieInteractor {

    override fun getPopular(): LiveData<PagedList<Movie>> {
        return movieRepository.getPopular()
    }
}