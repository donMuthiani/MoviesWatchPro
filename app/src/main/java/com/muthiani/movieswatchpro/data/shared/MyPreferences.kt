package com.muthiani.movieswatchpro.data.shared

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreferences
    @Inject
    constructor(private val sharedPreferences: SharedPreferences) {
        val isLoggedIn = "isLoggedIn"
        val onboarding = "Onboaarding"

        fun setIsLoggedIn(value: Boolean) {
            sharedPreferences.edit().putBoolean(isLoggedIn, value).apply()
        }

        fun getIsLoggedIn(): Boolean {
            return sharedPreferences.getBoolean(isLoggedIn, false)
        }

        fun isOnboardingCompleted(): Boolean {
            return sharedPreferences.getBoolean(onboarding, false)
        }

        fun setOnboardingCompleted() {
            sharedPreferences.edit().putBoolean(onboarding, true).apply()
        }
    }
