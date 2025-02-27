@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.presentation.watchlist

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.muthiani.movieswatchpro.presentation.components.LoadingScreen
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.presentation.components.customHomeTopBar
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme

@Composable
fun WatchListScreen(onMovieSelected: (Long) -> Unit) {
    val watchListViewModel: WatchListViewModel = hiltViewModel()

    val uiState by watchListViewModel.uiState.collectAsState()

    MoviesWatchProTheme {
        MoviesWatchScaffold(bottomBar = { }, topBar = { customHomeTopBar(false) }, content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                when (uiState) {
                    is WatchListViewModel.WatchListUiState.Loading -> {
                        LoadingScreen()
                    }

                    else -> {
//                        MovieCollectionItem(
//                            (uiState as WatchListViewModel.WatchListUiState.WatchList),
//                            onMovieSelected,
//                        )
                    }
                }
            }
        })
    }
}
