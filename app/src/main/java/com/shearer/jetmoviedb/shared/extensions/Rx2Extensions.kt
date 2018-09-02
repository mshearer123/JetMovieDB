package com.shearer.jetmoviedb.shared.extensions

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers(): Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.applyIoSchedulers(): Single<T> = subscribeOn(Schedulers.io()).observeOn(Schedulers.io())