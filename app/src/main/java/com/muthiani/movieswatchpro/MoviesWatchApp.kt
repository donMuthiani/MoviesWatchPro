package com.muthiani.movieswatchpro

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.muthiani.movieswatchpro.ui.MainDestinations
import com.muthiani.movieswatchpro.ui.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.ui.home.HomeSections
import com.muthiani.movieswatchpro.ui.home.MoviesWatchBottomBar
import com.muthiani.movieswatchpro.ui.home.addHomeGraph
import com.muthiani.movieswatchpro.ui.home.composableWithCompositionLocal
import com.muthiani.movieswatchpro.ui.home.nonSpatialExpressiveSpring
import com.muthiani.movieswatchpro.ui.home.spatialExpressiveSpring
import com.muthiani.movieswatchpro.ui.intro.OnboardingScreen
import com.muthiani.movieswatchpro.ui.rememberMoviesWatchNavController
import com.muthiani.movieswatchpro.ui.signup.SignUpScreen
import com.muthiani.movieswatchpro.ui.splash_screen.SplashScreenViewModel
import com.muthiani.movieswatchpro.ui.theme.LocalMoviesWatchColors
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoviesWatchApp() {
    val splashViewModel: SplashScreenViewModel = hiltViewModel()
    val isOnboarded = splashViewModel.isUserOnboarded
    val isLoggedIn by splashViewModel.loggedIn.collectAsState()
    val navController = rememberMoviesWatchNavController()

    SharedTransitionLayout {
        CompositionLocalProvider(value = LocalSharedTransitionScope provides this) {
            NavHost(
                navController = navController.navController,
                startDestination =
                when (isOnboarded) {
                    true -> {
                        if (isLoggedIn) MainDestinations.HOME_ROUTE else MainDestinations.LOGIN_ROUTE
                    }

                    else -> MainDestinations.ONBOARDING_ROUTE
                },
            ) {
                composableWithCompositionLocal(
                    route = MainDestinations.LOGIN_ROUTE,
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    },
                    enterTransition = {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    },
                ) {
                    SignUpScreen(
                        navController = navController,
                    )
                }
                composableWithCompositionLocal(
                    route = MainDestinations.ONBOARDING_ROUTE,
                    exitTransition = {
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    },
                    enterTransition = {
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    },
                ) {
                    OnboardingScreen(
                        onFinished = {
                            splashViewModel.setOnBoardingComplete()
                            navController.navigateToRoute(MainDestinations.LOGIN_ROUTE)
                        },
                    )
                }
                composableWithCompositionLocal(
                    route = MainDestinations.HOME_ROUTE,
                ) { backStackEntry ->
                    MainContainer(
                        onMovieSelected = navController::navigateToSnackDetail
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    onMovieSelected: (Long, String, NavBackStackEntry) -> Unit
) {

    val nestedNavController = rememberMoviesWatchNavController()
    val navBackStackEntry by nestedNavController.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val sharedTransitionScope = LocalSharedTransitionScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
        ?: throw IllegalStateException("No SharedElementScope found")
    MoviesWatchScaffold(
        bottomBar = {
            with(animatedVisibilityScope) {
                with(sharedTransitionScope) {
                    MoviesWatchBottomBar(
                        tabs = HomeSections.entries.toTypedArray(),
                        currentRoute = currentRoute ?: HomeSections.WATCH_LIST.route,
                        navigateToRoute = nestedNavController::navigateToBottomBarRoute,
                        modifier = Modifier
                            .renderInSharedTransitionScopeOverlay(
                                zIndexInOverlay = 1f,
                            )
                            .animateEnterExit(
                                enter = fadeIn(nonSpatialExpressiveSpring()) + slideInVertically(
                                    spatialExpressiveSpring()
                                ) {
                                    it
                                },
                                exit = fadeOut(nonSpatialExpressiveSpring()) + slideOutVertically(
                                    spatialExpressiveSpring()
                                ) {
                                    it
                                }
                            )
                    )
                }
            }
        },
        modifier = modifier
    ) { padding ->
        NavHost(
            navController = nestedNavController.navController,
            startDestination = HomeSections.WATCH_LIST.route,
        ) {
            addHomeGraph(
                onMovieSelected = onMovieSelected,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true)
@Composable
fun mainScreenPreview() {
    CompositionLocalProvider(LocalMoviesWatchColors provides MoviesWatchProTheme.colors) {
        SharedTransitionLayout {
            LocalSharedTransitionScope provides this
            MoviesWatchProTheme {
                MainContainer(modifier = Modifier) { movieId, movieTitle, navBackStackEntry ->
                    println("Movie selected: ID = $movieId, Title = $movieTitle")
                }
            }
        }
    }
}


val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
