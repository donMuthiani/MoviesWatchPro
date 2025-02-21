@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.ui.watchlist

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.muthiani.movieswatchpro.models.MovieModel
import com.muthiani.movieswatchpro.ui.components.LoadingScreen
import com.muthiani.movieswatchpro.ui.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.ui.components.customHomeTopBar
import com.muthiani.movieswatchpro.ui.discover.DiscoverItem
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

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
//                        MoviesWatchList(
//                            (uiState as WatchListViewModel.WatchListUiState.WatchList),
//                            onMovieSelected,
//                        )
                    }
                }
            }
        })
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoviesWatchList(
    movieList: LazyPagingItems<MovieModel>,
    onMovieClicked: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        Modifier
            .background(color = MoviesWatchProTheme.colors.uiBackground)
            .padding(bottom = 48.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        items(movieList.itemCount) {
            movieList[it]?.let { movie ->
                DiscoverItem(movie, onMovieClicked)
            }
        }

        movieList.apply {
            when (loadState.append) {
                is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }

                is LoadState.Error -> {
                    item {
                        Text("Error loading more movies!", color = Color.Red)
                    }
                }

                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun watchListScreenPreview() {
    WatchListScreen({})
}
