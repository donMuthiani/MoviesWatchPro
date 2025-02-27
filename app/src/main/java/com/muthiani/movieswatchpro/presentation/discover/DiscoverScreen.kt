package com.muthiani.movieswatchpro.presentation.discover

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.presentation.components.customHomeTopBar

@Composable
fun DiscoverScreen(
    onMovieSelected: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onMoreClicked: (String) -> Unit,
) {
    val discoverViewModel: DiscoverViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    val popularMoviesPagingItems = discoverViewModel.popularMoviesPagingDataFlow.collectAsLazyPagingItems()
    val upcomingMoviesPagingItems = discoverViewModel.upcomingMoviesPagingDataFlow.collectAsLazyPagingItems()
    val nowShowingMoviesPagingItems = discoverViewModel.nowShowingMoviesPagingDataFlow.collectAsLazyPagingItems()

    if (popularMoviesPagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                (popularMoviesPagingItems.loadState.refresh as LoadState.Error).error.message.orEmpty(),
            )
        }
    }

    MoviesWatchScaffold(topBar = { customHomeTopBar(true) }, content = { paddingValues ->
        LazyColumn(modifier = modifier.padding(paddingValues = paddingValues)) {
            item(key = "popular") {
                MovieCollectionItem(
                    movies = popularMoviesPagingItems,
                    onMoreClicked = onMoreClicked,
                    name = "popular",
                    onMovieClicked = onMovieSelected,
                )
            }

            item(key = "Now Showing") {
                MovieCollectionItem(
                    movies = nowShowingMoviesPagingItems,
                    onMoreClicked = onMoreClicked,
                    name = "Now Showing",
                    onMovieClicked = onMovieSelected,
                )
            }
        }
    }, bottomBar = {})
}
