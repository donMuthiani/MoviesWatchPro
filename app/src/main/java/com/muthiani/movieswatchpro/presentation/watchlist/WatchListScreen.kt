@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.presentation.watchlist

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.presentation.components.LoadingScreen
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.presentation.components.customHomeTopBar
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import kotlinx.coroutines.flow.Flow

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
    movieList: Flow<PagingData<MovieModel>>,
    onMovieClicked: (Long) -> Unit,
) {
    val movies = movieList.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        Modifier
            .background(color = MoviesWatchProTheme.colors.uiBackground)
            .padding(bottom = 48.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
//        items(movies.itemCount) { index ->
//            movies[index]?.let { movie ->
//                DiscoverItem(MovieModel(movie), onMovieClicked)
//            }
//        }

        item {
            when {
                movies.loadState.refresh is LoadState.Loading -> {
                    LoadingScreen()
                }

                movies.loadState.refresh is LoadState.Error -> {
                    val error = (movies.loadState.refresh as LoadState.Error).error
                    Text(
                        text = "Error: ${error.localizedMessage}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp),
                    )
                }

                movies.loadState.append is LoadState.Loading -> {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                movies.loadState.append is LoadState.Error -> {
                    ErrorWithRetry(message = "Failed to load more movies") {
                        movies.retry()
                    }
                    val error = (movies.loadState.append as LoadState.Error).error
                    Text(
                        text = "Failed to load more: ${error.localizedMessage}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun watchListScreenPreview() {
    WatchListScreen({})
}

@Composable
fun ErrorWithRetry(
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = message, color = Color.Red, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(text = "Retry", color = MoviesWatchProTheme.colors.textInteractive)
        }
    }
}
