package com.shearer.jetmoviedb.features.movie

import androidx.room.Room
import com.shearer.jetmoviedb.features.movie.db.MovieDb
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractor
import com.shearer.jetmoviedb.features.movie.interactor.MovieInteractorDefault
import com.shearer.jetmoviedb.features.movie.list.MovieListViewModel
import com.shearer.jetmoviedb.features.movie.repository.MovieRepository
import com.shearer.jetmoviedb.features.movie.repository.MovieRepositoryDefault
import com.shearer.jetmoviedb.shared.http.RestServiceDefault
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val movieModule = module {
    single { Room.databaseBuilder(get(), MovieDb::class.java, "movie-db").build() }
    factory { MovieRepositoryDefault(get(), get()) as MovieRepository }
    factory { MovieInteractorDefault(get()) as MovieInteractor }
    factory { RestServiceDefault(get()).createMovieApi() }

    viewModel {
        MovieListViewModel(get())
    }
}
