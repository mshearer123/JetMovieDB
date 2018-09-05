package com.shearer.jetmoviedb.features.main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.common.putSearch
import com.shearer.jetmoviedb.features.movie.list.MovieListFragment
import com.shearer.jetmoviedb.shared.extensions.observeIt
import com.shearer.jetmoviedb.shared.extensions.onQueryTextSubmit
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, MovieListFragment()).commitNow()
        }
        model.searchFocus.observeIt(this, ::hideKeyboard)
        model.launchSearch.observeIt(this, ::launchSearch)
        model.launchPopular.observeIt(this) { launchPopular() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.onQueryTextSubmit { model.onTextEntered(it) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_popular -> model.popularClicked()
        }
        return false
    }

    private fun launchSearch(searchTerm: String) {
        val args = Bundle().putSearch(searchTerm)
        val fragment = MovieListFragment().apply { arguments = args }
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commitNow()
    }

    private fun launchPopular() {
        supportFragmentManager.beginTransaction().replace(R.id.container, MovieListFragment()).commitNow()
    }

    private fun hideKeyboard(hideKeyboard: Boolean) {
        if (hideKeyboard) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(container.windowToken, 0)
        }

    }
}
