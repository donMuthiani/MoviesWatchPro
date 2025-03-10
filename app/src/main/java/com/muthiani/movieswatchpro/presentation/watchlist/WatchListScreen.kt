@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.presentation.watchlist

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.muthiani.movieswatchpro.data.config.ApiLoadTypeHolder
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.presentation.components.customHomeTopBar
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import com.muthiani.movieswatchpro.presentation.viewMore.ViewMoreListContent

@Composable
fun WatchListScreen(
    onMovieSelected: (Long) -> Unit,
    apiTypeHolder: ApiLoadTypeHolder,
) {
    val viewModel: WatchListViewModel = hiltViewModel()

    apiTypeHolder.apiType = "watchlist"

    val watchListPagingItems = viewModel.watchListMoviesPagingDataFlow.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    if (watchListPagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                (watchListPagingItems.loadState.refresh as LoadState.Error).error.message.orEmpty(),
            )
        }
    }

    MoviesWatchProTheme {
        MoviesWatchScaffold(
            bottomBar = { },
            topBar = { customHomeTopBar(false) },
            content = { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(start = 8.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "WatchList",
                            style = MaterialTheme.typography.titleLarge,
                            color = MoviesWatchProTheme.colors.brand,
                            modifier =
                                Modifier
                                    .padding(start = 12.dp),
                        )
                    }

                    if (watchListPagingItems.loadState.refresh is LoadState.Loading) {
                        CircularProgressIndicator()
                    } else {
                        ViewMoreListContent(
                            watchListPagingItems,
                            onMovieSelected,
                        )
                    }
                }
            },
        )
    }
}
