package com.shearer.jetmoviedb.shared.extensions

import androidx.appcompat.widget.SearchView

fun SearchView.onQueryTextSubmit(queryEntered: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            query?.let {
                queryEntered.invoke(it)
                return true
            }
            return false
        }

        override fun onQueryTextChange(newText: String?) = false
    })
}