package com.shearer.jetmoviedb.shared.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> LiveData<T>.observeIt(owner: LifecycleOwner, crossinline observer: (T) -> Unit) {
    this.observe(owner, Observer { observer(it) })
}