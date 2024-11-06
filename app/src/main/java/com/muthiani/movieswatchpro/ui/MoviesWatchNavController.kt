package com.muthiani.movieswatchpro.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val MOVIE_DETAIL_ROUTE = "movie"
    const val MOVIE_ID_KEY = "movieId"
    const val ORIGIN = "origin"
    const val LOGIN_ROUTE = "login"
    const val ONBOARDING_ROUTE = "onboarding"
}

@Composable
fun rememberMoviesWatchNavController(
    navController: NavHostController = rememberNavController()
): MoviesWatchNavController = remember(navController) {
    MoviesWatchNavController(navController)
}

@Stable
class MoviesWatchNavController(
    val navController: NavHostController
) {
    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToBottomBarRoute(route: String) {
        if (route != navController.currentDestination?.route) {
            navController.navigate(route) {
                launchSingleTop
                restoreState

                popUpTo(findStartDestination(graph = navController.graph).id) {
                    saveState = true
                }
            }
        }
    }

    fun navigateToSnackDetail(movieID: Long, origin: String, from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${MainDestinations.MOVIE_DETAIL_ROUTE}/$movieID?origin=$origin")
        }
    }

    fun navigateToRoute(route: String, noTrace: Boolean = false) {
        navController.navigate(route) {
            launchSingleTop
            restoreState
            if (noTrace) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }
    }

    private fun NavBackStackEntry.lifecycleIsResumed() =
        this.lifecycle.currentState == Lifecycle.State.RESUMED

    private val NavGraph.startDestination: NavDestination?
        get() = findNode(startDestinationId)

    private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
        return if (graph is NavGraph) findStartDestination(graph.findStartDestination()) else graph
    } }
