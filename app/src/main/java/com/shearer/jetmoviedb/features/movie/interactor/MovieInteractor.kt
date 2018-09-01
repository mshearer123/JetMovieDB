package com.shearer.jetmoviedb.features.movie.interactor

import com.shearer.jetmoviedb.features.movie.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import io.reactivex.Single

interface MovieInteractor {
    fun getPopular(page: Long): Single<MovieResults>
}

class MovieInteractorDefault(private val movieRepository: MovieRepository) : MovieInteractor {

    override fun getPopular(page: Long): Single<MovieResults> {
        return movieRepository.getPopular(page)
    }
}