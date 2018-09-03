package com.shearer.jetmoviedb.features.movie

import androidx.room.Room
import com.shearer.jetmoviedb.features.movie.common.db.MovieDb
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractor
import com.shearer.jetmoviedb.features.movie.common.interactor.MovieInteractorDefault
import com.shearer.jetmoviedb.features.movie.common.repository.MovieRepository
import com.shearer.jetmoviedb.features.movie.common.repository.MovieRepositoryDefault
import com.shearer.jetmoviedb.features.movie.detail.MovieDetailViewModel
import com.shearer.jetmoviedb.features.movie.list.MovieListViewModel
import com.shearer.jetmoviedb.shared.http.RestServiceDefault
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val movieModule = module {
    single { Room.databaseBuilder(get(), MovieDb::class.java, "movie-db").build() }
    factory { MovieRepositoryDefault(get()) as MovieRepository }
    factory { MovieInteractorDefault(get(), get()) as MovieInteractor }
    factory { RestServiceDefault(get()).createMovieApi() }

    viewModel { MovieListViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
}
