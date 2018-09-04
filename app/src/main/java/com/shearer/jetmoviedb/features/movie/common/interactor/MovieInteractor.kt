package com.shearer.jetmoviedb.features.movie.common.interactor

import androidx.paging.DataSource
import com.shearer.jetmoviedb.features.movie.common.domain.Movie
import com.shearer.jetmoviedb.features.movie.common.domain.MovieDetail
import com.shearer.jetmoviedb.features.movie.common.domain.MovieResults
import com.shearer.jetmoviedb.features.movie.common.repository.MovieDbRepository
import com.shearer.jetmoviedb.features.movie.common.repository.MovieRepository
import com.shearer.jetmoviedb.features.movie.list.SearchInfo
import io.reactivex.Completable
import io.reactivex.Single

interface MovieInteractor {
    fun getMovieDetails(id: Int): Single<MovieDetail>
    fun getMovies(searchInfo: SearchInfo): Single<MovieResults>
    fun getDataSource(searchInfo: SearchInfo): DataSource.Factory<Int, Movie>
    fun saveMovies(searchInfo: SearchInfo, movieResults: MovieResults)
    fun deleteMovieCache(searchInfo: SearchInfo): Completable
}

class MovieInteractorDefault(private val movieRepository: MovieRepository, private val movieDbRepository: MovieDbRepository) : MovieInteractor {

    override fun getMovies(searchInfo: SearchInfo): Single<MovieResults> {
        return movieDbRepository.getNextPage(searchInfo).flatMap {
            when (searchInfo.type) {
                SearchInfo.Type.POPULAR -> {
                    movieRepository.getPopular(it)
                }
                SearchInfo.Type.SEARCH -> {
                    movieRepository.getMoviesBySearchTerm(it, searchInfo.term!!)
                }
            }
        }
    }

    override fun getMovieDetails(id: Int): Single<MovieDetail> = movieRepository.getMovieDetails(id)

    override fun getDataSource(searchInfo: SearchInfo) = movieDbRepository.getMovieDataSource(searchInfo)

    override fun saveMovies(searchInfo: SearchInfo, movieResults: MovieResults) = movieDbRepository.insertMoviesInDatabase(searchInfo, movieResults)

    override fun deleteMovieCache(searchInfo: SearchInfo) = movieDbRepository.deleteMovieCache(searchInfo)

}