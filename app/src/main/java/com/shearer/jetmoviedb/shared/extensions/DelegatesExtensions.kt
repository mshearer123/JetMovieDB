package com.shearer.jetmoviedb.shared.extensions

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty

inline fun <T> Delegates.observable(initialValue: T, crossinline block: () -> Unit): ReadWriteProperty<Any?, T> {
    return observable(initialValue) { _, _, _ -> block() }
}