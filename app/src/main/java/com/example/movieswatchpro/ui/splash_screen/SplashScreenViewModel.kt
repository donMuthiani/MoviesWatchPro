package com.example.movieswatchpro.ui.splash_screen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieswatchpro.shared.MyPreferences
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val sharedPreferences: MyPreferences) : ViewModel() {
    private val splashShowFlow = MutableStateFlow(true)
    val isSplashShow = splashShowFlow.asStateFlow()

    init {
        val isOnboarded = sharedPreferences.isOnboardingCompleted()

        viewModelScope.launch {
            splashShowFlow.value = !isOnboarded
        }
    }


    fun setOnBoardingComplete() {
        sharedPreferences.setOnboardingCompleted()
        splashShowFlow.value = false
    }


}
