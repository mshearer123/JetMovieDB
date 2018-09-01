package com.shearer.jetmoviedb.features.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.list.MovieListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, MovieListFragment()).commitNow()
        }
    }

}
