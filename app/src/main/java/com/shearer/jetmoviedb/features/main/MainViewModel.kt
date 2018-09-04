package com.shearer.jetmoviedb.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var searchFocus = MutableLiveData<Boolean>()
    var launchSearch = MutableLiveData<String>()

    fun onTextEntered(search: String) {
        if (search.isNotEmpty()) {
            launchSearch.value = search
        }
        searchFocus.value = false
    }
}
