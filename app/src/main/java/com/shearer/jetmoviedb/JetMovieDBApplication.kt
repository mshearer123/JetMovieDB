package com.shearer.jetmoviedb

import android.app.Application
import com.facebook.stetho.Stetho
import com.shearer.jetmoviedb.features.main.mainModule
import com.shearer.jetmoviedb.features.movie.movieModule
import com.shearer.jetmoviedb.shared.sharedModule
import org.koin.android.ext.android.startKoin

class JetMovieDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(mainModule, sharedModule, movieModule))
        addStetho()
    }

    private fun Application.addStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}