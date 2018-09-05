package com.shearer.jetmoviedb.features.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var searchFocus = MutableLiveData<Boolean>()
    var launchSearch = MutableLiveData<String>()
    var launchPopular = MutableLiveData<Boolean>()

    fun onTextEntered(search: String?) {
        if (search.isNullOrEmpty().not()) {
            launchSearch.value = search
        }
        searchFocus.value = false
    }

    fun popularClicked() {
        launchPopular.value = true
        searchFocus.value = false
    }
}
