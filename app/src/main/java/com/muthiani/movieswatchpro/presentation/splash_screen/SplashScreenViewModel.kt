package com.muthiani.movieswatchpro.presentation.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muthiani.movieswatchpro.data.sharedPrefs.MyPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel
    @Inject
    constructor(private val sharedPreferences: MyPreferences) : ViewModel() {
        var _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
        val loggedIn: StateFlow<Boolean> = _isLoggedIn

        init {
            viewModelScope.launch {
                _isLoggedIn.value = sharedPreferences.getIsLoggedIn()
            }
        }

        val isUserOnboarded = sharedPreferences.isOnboardingCompleted()

        fun setOnBoardingComplete() {
            sharedPreferences.setOnboardingCompleted()
        }

        fun setUserLoggedIn() {
            sharedPreferences.setIsLoggedIn(true)
        }
    }
