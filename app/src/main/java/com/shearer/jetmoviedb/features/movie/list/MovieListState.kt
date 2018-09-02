package com.shearer.jetmoviedb.features.movie.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.shearer.jetmoviedb.features.movie.common.domain.Movie

data class MovieListState(
        val pagedList: LiveData<PagedList<Movie>>,
        val refreshing: LiveData<Boolean>)