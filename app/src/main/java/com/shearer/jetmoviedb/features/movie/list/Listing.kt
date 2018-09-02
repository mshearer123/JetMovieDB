package com.shearer.jetmoviedb.features.movie.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class Listing<T>(
        val pagedList: LiveData<PagedList<T>>,
        val refreshing: LiveData<Boolean>)