package com.shearer.jetmoviedb.shared.extensions

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Single<T>.applySchedulers(): Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.applySchedulers(): Completable = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

inline fun Completable.doOnSuccessOrError(crossinline block: () -> Unit): Completable = doOnEvent { block() }