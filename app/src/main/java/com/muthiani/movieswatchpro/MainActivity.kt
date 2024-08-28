package com.muthiani.movieswatchpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muthiani.movieswatchpro.ui.home.home
import com.muthiani.movieswatchpro.ui.intro.onboardingScreen
import com.muthiani.movieswatchpro.ui.signup.signUpScreen
import com.muthiani.movieswatchpro.ui.splash_screen.SplashScreenViewModel
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesWatchProTheme {
                moviesWatch()
            }
        }
    }

    @Composable
    fun moviesWatch() {
        val isOnboarded = splashViewModel.isUserOnboarded
        val isLoggedIn by splashViewModel.loggedIn.collectAsState()
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination =
            when (isOnboarded) {
                true -> {
                    if (isLoggedIn) "home" else "login"
                }

                else -> "onboarding"
            },
        ) {
            composable(
                route = "login",
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                },
            ) {
                signUpScreen(
                    navController = navController,
                )
            }
            composable(
                route = "onboarding",
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                },
            ) {
                onboardingScreen(
                    onFinished = {
                        splashViewModel.setOnBoardingComplete()
                        navController.navigate(route = "login")
                    },
                )
            }
            composable(
                route = "home",
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                },
            ) {
                home()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun mainScreenPreview() {
        moviesWatch()
    }

    @Composable
    fun homeScreen() {
        Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
            Column(
                modifier =
                Modifier
                    .padding(top = 24.dp) // Add padding for status bar
                    .fillMaxSize(),
            ) {
                Text(text = "Home Screen", style = MaterialTheme.typography.titleLarge)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun homeScreenPreview() {
        homeScreen()
    }
}
