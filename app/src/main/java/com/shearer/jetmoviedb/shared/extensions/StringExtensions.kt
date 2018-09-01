package com.shearer.jetmoviedb.shared.extensions

import android.content.res.Resources
import android.support.annotation.StringRes
import com.shearer.jetmoviedb.JetMovieDBApplication

private val resources: Resources
    get() = JetMovieDBApplication.instance.resources

fun getString(@StringRes id: Int, vararg args: Any): String = resources.getString(id, *args)
