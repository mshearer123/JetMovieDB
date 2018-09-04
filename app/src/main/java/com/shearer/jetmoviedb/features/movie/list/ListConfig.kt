package com.shearer.jetmoviedb.features.movie.list

import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.shared.extensions.getString

interface ListConfig {
    fun getType(): String
}

class SearchConfig(val searchTerm: String) : ListConfig {
    override fun getType() = getString(R.string.search_term_tag, searchTerm)
}

class PopularConfig : ListConfig {
    override fun getType() = getString(R.string.popular_tag)
}