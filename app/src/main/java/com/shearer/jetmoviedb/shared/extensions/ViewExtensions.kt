package com.shearer.jetmoviedb.shared.extensions

import android.view.View

private const val DEFAULT_DURATION = 250L
private const val VIEW_SHOW = 1F
private const val VIEW_HIDE = 0F

fun View.fadeIn() {
    animate().setDuration(DEFAULT_DURATION).alpha(VIEW_SHOW)
}

fun View.fadeOut() {
    animate().setDuration(DEFAULT_DURATION).alpha(VIEW_HIDE)
}

