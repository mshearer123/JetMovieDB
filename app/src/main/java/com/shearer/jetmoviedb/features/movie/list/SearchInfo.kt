package com.shearer.jetmoviedb.features.movie.list

data class SearchInfo(val type: Type, val term: String? = null) {

    enum class Type {
        POPULAR, SEARCH
    }
}

