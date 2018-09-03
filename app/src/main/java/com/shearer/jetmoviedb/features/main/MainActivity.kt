package com.shearer.jetmoviedb.features.main

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shearer.jetmoviedb.R
import com.shearer.jetmoviedb.features.movie.list.MovieListFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.container, MovieListFragment()).commitNow()
        }

        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mainViewModel.onTextEntered(editText.text.toString())
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        mainViewModel.searchFocus.observe(this, Observer {
            if (it) {

            } else {
                editText.clearFocus()
                hideKeyboard()
            }
        })
        mainViewModel.launchSearch.observe(this, Observer {
            val args = Bundle().apply { putString("SEARCH", it) }
            val fragment = MovieListFragment().apply { arguments = args }
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commitNow()
        })
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }


}
