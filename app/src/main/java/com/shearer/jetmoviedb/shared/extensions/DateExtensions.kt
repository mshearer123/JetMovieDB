package com.shearer.jetmoviedb.shared.extensions

import java.util.*
import java.util.Calendar.YEAR

fun Date.resolveYear(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(YEAR).toString()
}
