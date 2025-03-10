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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.muthiani.movieswatchpro.data.config.ApiLoadTypeHolder
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.presentation.detail.MovieDetailScreen
import com.muthiani.movieswatchpro.presentation.detail.nonSpatialExpressiveSpring
import com.muthiani.movieswatchpro.presentation.detail.spatialExpressiveSpring
import com.muthiani.movieswatchpro.presentation.home.HomeSections
import com.muthiani.movieswatchpro.presentation.home.MoviesWatchBottomBar
import com.muthiani.movieswatchpro.presentation.home.addHomeGraph
import com.muthiani.movieswatchpro.presentation.home.composableWithCompositionLocal
import com.muthiani.movieswatchpro.presentation.intro.OnboardingScreen
import com.muthiani.movieswatchpro.presentation.navigation.MainDestinations
import com.muthiani.movieswatchpro.presentation.navigation.rememberMoviesWatchNavController
import com.muthiani.movieswatchpro.presentation.signup.SignUpScreen
import com.muthiani.movieswatchpro.presentation.splash_screen.SplashScreenViewModel
import com.muthiani.movieswatchpro.presentation.viewMore.GenericMovieListScreen

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoviesWatchApp(apiTypeHolder: ApiLoadTypeHolder) {
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
                        apiTypeHolder = apiTypeHolder,
                        onMovieSelected = navController::navigateToMovieDetail,
                        onMoreClicked = navController::navigateToMovieViewer,
                        upPress = {navController.upPress()}
                    )
                }

                composableWithCompositionLocal(
                    route = "${MainDestinations.MOVIE_LIST_VIEWER}/{${MainDestinations.API_CALL_TYPE}}",
                    arguments =
                        listOf(
                            navArgument(MainDestinations.API_CALL_TYPE) {
                                type = NavType.StringType
                            },
                        ),
                ) { backStackEntry ->
                    val apiCallType = backStackEntry.arguments?.getString(MainDestinations.API_CALL_TYPE)
                    GenericMovieListScreen(
                        apiTypeHolder = apiTypeHolder,
                        onMovieSelected = navController::navigateToMovieDetail,
                        apiCallType = apiCallType ?: "",
                        upPress = { navController.upPress() },
                    )
                }

                composableWithCompositionLocal(
                    route = "${MainDestinations.MOVIE_DETAIL_ROUTE}/{${MainDestinations.MOVIE_ID_KEY}}",
                    arguments =
                        listOf(
                            navArgument(MainDestinations.MOVIE_ID_KEY) {
                                type = NavType.LongType
                            },
                        ),
                ) { navBackStackEntry ->
                    val arguments = requireNotNull(navBackStackEntry.arguments)
                    val movieId = arguments.getLong(MainDestinations.MOVIE_ID_KEY)
                    MovieDetailScreen(
                        movieId = movieId,
                        upPress = { navController.upPress() },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainContainer(
    upPress: () -> Unit,
    apiTypeHolder: ApiLoadTypeHolder,
    modifier: Modifier = Modifier,
    onMovieSelected: (Long, NavBackStackEntry) -> Unit,
    onMoreClicked: (String) -> Unit = {},
) {
    val nestedNavController = rememberMoviesWatchNavController()
    val navBackStackEntry by nestedNavController.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val sharedTransitionScope =
        LocalSharedTransitionScope.current
            ?: throw IllegalStateException("No SharedElementScope found")
    val animatedVisibilityScope =
        LocalNavAnimatedVisibilityScope.current
            ?: throw IllegalStateException("No SharedElementScope found")
    MoviesWatchScaffold(
        bottomBar = {
            with(animatedVisibilityScope) {
                with(sharedTransitionScope) {
                    MoviesWatchBottomBar(
                        tabs = HomeSections.entries.toTypedArray(),
                        currentRoute = currentRoute ?: HomeSections.WATCH_LIST.route,
                        navigateToRoute = nestedNavController::navigateToBottomBarRoute,
                        modifier =
                            Modifier
                                .renderInSharedTransitionScopeOverlay(
                                    zIndexInOverlay = 1f,
                                )
                                .animateEnterExit(
                                    enter =
                                        fadeIn(nonSpatialExpressiveSpring()) +
                                            slideInVertically(
                                                spatialExpressiveSpring(),
                                            ) {
                                                it
                                            },
                                    exit =
                                        fadeOut(nonSpatialExpressiveSpring()) +
                                            slideOutVertically(
                                                spatialExpressiveSpring(),
                                            ) {
                                                it
                                            },
                                ),
                    )
                }
            }
        },
        modifier = modifier,
    ) { padding ->
        NavHost(
            navController = nestedNavController.navController,
            startDestination = HomeSections.DISCOVER.route,
        ) {
            addHomeGraph(
                upPress = upPress,
                apiTypeHolder = apiTypeHolder,
                onMovieSelected = onMovieSelected,
                modifier =
                    Modifier
                        .padding(padding)
                        .consumeWindowInsets(padding),
                onMoreClicked = onMoreClicked,
            )
        }
    }
}

val LocalNavAnimatedVisibilityScope = compositionLocalOf<AnimatedVisibilityScope?> { null }

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }
