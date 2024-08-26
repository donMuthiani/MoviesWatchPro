package com.muthiani.movieswatchpro.ui.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muthiani.movieswatchpro.shared.MyPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(private val sharedPreferences: MyPreferences) : ViewModel() {

    val isUserOnboarded = sharedPreferences.isOnboardingCompleted()
    val isLoggedIn = sharedPreferences.getIsLoggedIn()

    fun setOnBoardingComplete() {
        sharedPreferences.setOnboardingCompleted()
    }

}
