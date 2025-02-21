@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.ui.discover

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muthiani.movieswatchpro.models.MovieCollection
import com.muthiani.movieswatchpro.ui.components.*

@Composable
fun DiscoverScreen(
    onMovieSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onMoreClicked: (String) -> Unit,
) {
    val discoverViewModel: DiscoverViewModel = hiltViewModel()
    val uiState = discoverViewModel.uiState.collectAsState().value
    var showDialog by remember { mutableStateOf(true) }
    var showProgress by remember { mutableStateOf(true) }

    MoviesWatchScaffold(topBar = { customHomeTopBar(true) }, content = { paddingValues ->
        Column(modifier = modifier.padding(paddingValues = paddingValues)) {
            when (uiState) {
                is DiscoverViewModel.DiscoverUiState.Success -> {
                    showProgress = false
                    DiscoverContent(uiState.collection, onMovieSelected, onMoreClicked)
                }

                is DiscoverViewModel.DiscoverUiState.Loading -> {
                    if (showProgress) {
                        LoadingScreen()
                    }
                }

                is DiscoverViewModel.DiscoverUiState.Error -> {
                    showProgress = false
                    if (showDialog) {
                        ErrorScreen(onDismiss = {
                            showDialog = false
                            discoverViewModel.getMovies()
                        }, errorMessage = uiState.message)
                    }
                }
                else -> {}
            }
        }
    }, bottomBar = {})
}

@Composable
fun DiscoverContent(
    uiState: List<MovieCollection>,
    onMovieSelected: (Long) -> Unit,
    onMoreClicked: (String) -> Unit,
) {
    ContentList(uiState, onMovieSelected, onMoreClicked)
}

@Composable
fun ContentList(
    uiState: List<MovieCollection>,
    onMovieSelected: (Long) -> Unit,
    onMoreClicked: (String) -> Unit,
) {
    LazyColumn(modifier = Modifier) {
        itemsIndexed(uiState) { index, collection ->
            if (index > 0) {
                MoviesWatchDivider(thickness = 2.dp)
            }

            MovieCollectionItem(
                movieCollection =
                    MovieCollection(
                        name = collection.name,
                        movies = collection.movies,
                        onMovieClicked = onMovieSelected,
                    ),
                onMoreClicked = onMoreClicked,
            )
        }
    }
}
