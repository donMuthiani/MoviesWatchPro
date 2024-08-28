package com.muthiani.movieswatchpro.ui.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun Home() {
    val bottomNavItems =
        listOf(
            BottomNavItem.WatchList,
            BottomNavItem.MyShows,
            BottomNavItem.Discover,
            BottomNavItem.Statistics,
        )
    val navController = rememberNavController()

    MoviesWatchProTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Scaffold(bottomBar = {
                TabView(
                    tabBarItems = bottomNavItems,
                    navController = navController,
                )
            }) {
                NavHost(
                    navController = navController,
                    startDestination = BottomNavItem.WatchList.route,
                ) {
                    composable(
                        BottomNavItem.WatchList.route,
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                        },
                        enterTransition = {
                            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                        },
                    ) {
                        watchListScreen(navController = navController)
                    }
                    composable(
                        BottomNavItem.MyShows.route,
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                        },
                        enterTransition = {
                            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                        },
                    ) {
                        Text(text = BottomNavItem.MyShows.label)
                    }

                    composable(
                        BottomNavItem.Discover.route,
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                        },
                        enterTransition = {
                            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                        },
                    ) {
                        Text(text = BottomNavItem.Discover.label)
                    }

                    composable(
                        BottomNavItem.Statistics.route,
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                        },
                        enterTransition = {
                            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                        },
                    ) {
                        Text(text = BottomNavItem.Statistics.label)
                    }
                }
            }
        }
    }
}

@Composable
fun TabView(
    tabBarItems: List<BottomNavItem>,
    navController: NavController,
) {
    var selectedIndex by rememberSaveable {
        mutableStateOf(0)
    }

    NavigationBar {
        tabBarItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(selected = selectedIndex == index, onClick = {
                selectedIndex = index
                navController.navigate(bottomNavItem.route)
            }, icon = {
                Icon(imageVector = bottomNavItem.icon, contentDescription = bottomNavItem.label)
            }, label = { Text(text = bottomNavItem.label) })
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object WatchList : BottomNavItem("watchList", Icons.Default.Home, "WatchList")

    data object MyShows : BottomNavItem("myShows", Icons.Default.Filter, "MyShows")

    data object Discover : BottomNavItem(route = "Discover", Icons.Default.Search, "Discover")

    data object Statistics : BottomNavItem(route = "Statistics", Icons.Default.Person, "Statistics")
}
