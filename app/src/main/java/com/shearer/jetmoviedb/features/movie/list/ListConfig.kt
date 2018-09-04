package com.shearer.jetmoviedb.features.movie.list

interface ListConfig {
    fun getType(): String
}

class SearchConfig(val searchTerm: String) : ListConfig {
    override fun getType() = "s-$searchTerm"
}

class PopularConfig : ListConfig {
    override fun getType() = "popular"
}