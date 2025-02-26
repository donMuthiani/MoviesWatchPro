@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.presentation.discover

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.presentation.components.customHomeTopBar

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
    val snackbarHostState = remember { SnackbarHostState() }

    val popularMoviesPagingItems = discoverViewModel.popularMoviesPagingDataFlow.collectAsLazyPagingItems()

    if (popularMoviesPagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                (popularMoviesPagingItems.loadState.refresh as LoadState.Error).error.message.orEmpty(),
            )
        }
    }

    MoviesWatchScaffold(topBar = { customHomeTopBar(true) }, content = { paddingValues ->
        Column(modifier = modifier.padding(paddingValues = paddingValues)) {
            DiscoverContent(popularMoviesPagingItems, onMovieSelected, onMoreClicked)
//            when (uiState) {
//                is DiscoverViewModel.DiscoverUiState.Success -> {
//                    showProgress = false
//                    DiscoverContent(popularMoviesPagingItems, onMovieSelected, onMoreClicked)
//                }
//
//                is DiscoverViewModel.DiscoverUiState.Loading -> {
//                    if (showProgress) {
//                        LoadingScreen()
//                    }
//                }
//
//                is DiscoverViewModel.DiscoverUiState.Error -> {
//                    showProgress = false
//                    if (showDialog) {
//                        ErrorScreen(onDismiss = {
//                            showDialog = false
//                            discoverViewModel.getMovies()
//                        }, errorMessage = uiState.message)
//                    }
//                }
//
//                else -> {}
//            }
        }
    }, bottomBar = {})
}

@Composable
fun DiscoverContent(
    popularMoviesPagingItems: LazyPagingItems<MovieModel>,
    onMovieSelected: (Long) -> Unit,
    onMoreClicked: (String) -> Unit,
) {
    ContentList(popularMoviesPagingItems, onMovieSelected, onMoreClicked)
}

@Composable
fun ContentList(
    popularMoviesPagingItems: LazyPagingItems<MovieModel>,
    onMovieSelected: (Long) -> Unit,
    onMoreClicked: (String) -> Unit,
) {
    if (popularMoviesPagingItems.loadState.refresh is LoadState.Loading) {
        CircularProgressIndicator()
    } else {
        MovieCollectionItem(
            movies = popularMoviesPagingItems,
            onMoreClicked = onMoreClicked,
            onMovieClicked = onMovieSelected,
        )
    }
}
