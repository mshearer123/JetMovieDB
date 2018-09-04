package com.shearer.jetmoviedb.features.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class MainViewModelTest {

    @Rule @JvmField var rule: TestRule = InstantTaskExecutorRule()

    private val stringObserver = mock<Observer<String>>()
    private val model by lazy { MainViewModel() }

    @Test fun onTextEntered_launchSearch() {
        model.run {
            onTextEntered("example")
            assertThat(launchSearch.value).isEqualTo("example")
        }
    }

    @Test fun onTextEntered_emptyString_doNotLaunchSearch() {
        model.run {
            launchSearch.observeForever(stringObserver)
            onTextEntered("")
            verify(stringObserver, never()).onChanged(any())
        }
    }

    @Test fun onTextEntered_loseSearchFocus() {
        model.run {
            onTextEntered("")
            assertThat(model.searchFocus.value).isFalse()
        }
    }

}