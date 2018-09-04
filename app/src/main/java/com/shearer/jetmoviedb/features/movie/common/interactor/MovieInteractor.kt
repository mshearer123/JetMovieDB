package com.shearer.jetmoviedb.features.movie.common.interactor

import androidx.paging.DataSource
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.domain.MovieDetail
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbRepository
import com.shearer.jetmoviedb.features.movie.common.repository.MovieRepository
import com.shearer.jetmoviedb.features.movie.list.ListConfig
import com.shearer.jetmoviedb.features.movie.list.SearchConfig
import io.reactivex.Completable
import io.reactivex.Single

interface MovieInteractor {
    fun getMovieDetails(id: Int): Single<MovieDetail>
    fun getMovies(config: ListConfig): Single<MovieResults>
    fun getDataSource(config: ListConfig): DataSource.Factory<Int, Movie>
    fun saveMovies(config: ListConfig, movieResults: MovieResults)
    fun deleteMovieCache(config: ListConfig): Completable
}

class MovieInteractorDefault(private val movieRepository: MovieRepository, private val movieDbRepository: MovieDbRepository) : MovieInteractor {

    override fun getMovies(config: ListConfig): Single<MovieResults> {
        return movieDbRepository.getNextPage(config).flatMap {
            when (config) {
                is SearchConfig -> {
                    movieRepository.getMoviesBySearchTerm(it, config.searchTerm)
                }
                else -> {
                    movieRepository.getPopular(it)
                }

            }
        }
    }

    override fun getMovieDetails(id: Int): Single<MovieDetail> = movieRepository.getMovieDetails(id)

    override fun getDataSource(config: ListConfig) = movieDbRepository.getMovieDataSource(config)

    override fun saveMovies(config: ListConfig, movieResults: MovieResults) = movieDbRepository.insertMoviesInDatabase(config, movieResults)

    override fun deleteMovieCache(config: ListConfig) = movieDbRepository.deleteMovieCache(config)

}