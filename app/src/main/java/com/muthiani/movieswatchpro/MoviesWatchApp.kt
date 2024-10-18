package com.muthiani.movieswatchpro

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muthiani.movieswatchpro.ui.home.Home
import com.muthiani.movieswatchpro.ui.intro.onboardingScreen
import com.muthiani.movieswatchpro.ui.signup.signUpScreen
import com.muthiani.movieswatchpro.ui.splash_screen.SplashScreenViewModel

@Composable
fun MoviesWatchApp() {
    val splashViewModel: SplashScreenViewModel = hiltViewModel()
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
            Home()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun mainScreenPreview() {
    MoviesWatchApp()
}