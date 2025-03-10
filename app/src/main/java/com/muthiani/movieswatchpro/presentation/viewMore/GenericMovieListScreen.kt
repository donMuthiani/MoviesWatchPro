package com.muthiani.movieswatchpro.presentation.viewMore

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.muthiani.movieswatchpro.data.config.ApiLoadTypeHolder
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.presentation.components.customHomeTopBar
import com.muthiani.movieswatchpro.presentation.discover.DiscoverItem
import com.muthiani.movieswatchpro.presentation.discover.MoviesWatchViewModel
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import com.muthiani.movieswatchpro.presentation.utils.toCamelCase

@Composable
fun GenericMovieListScreen(
    apiCallType: String,
    apiTypeHolder: ApiLoadTypeHolder,
    onMovieSelected: (Long) -> Unit,
    upPress: () -> Unit,
) {
    apiTypeHolder.apiType = apiCallType

    val viewModel = hiltViewModel<MoviesWatchViewModel>()

    val viewMoreMoviesPagingItems = viewModel.viewMoreMoviesPagingDataFlow.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    if (viewMoreMoviesPagingItems.loadState.refresh is LoadState.Error) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar(
                (viewMoreMoviesPagingItems.loadState.refresh as LoadState.Error).error.message.orEmpty(),
            )
        }
    }

    MoviesWatchProTheme {
        MoviesWatchScaffold(
            topBar = { customHomeTopBar(showActions = false) },
            content = { paddingValues ->

                Column(
                    modifier = Modifier.padding(paddingValues),
                ) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(start = 8.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Navigate back",
                            tint = MoviesWatchProTheme.colors.textInteractive,
                            modifier =
                                Modifier
                                    .size(24.dp)
                                    .clickable {
                                        upPress.invoke()
                                    },
                        )

                        Text(
                            text = apiCallType.toCamelCase(),
                            style = MaterialTheme.typography.titleLarge,
                            color = MoviesWatchProTheme.colors.brand,
                            modifier =
                                Modifier
                                    .padding(start = 12.dp),
                        )
                    }

                    if (viewMoreMoviesPagingItems.loadState.refresh is LoadState.Loading) {
                        CircularProgressIndicator()
                    } else {
                        ViewMoreListContent(
                            viewMoreMoviesPagingItems,
                            onMovieSelected,
                        )
                    }
                }
            },
            bottomBar = {},
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ViewMoreListContent(
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
        items(movieList.itemCount, key = movieList.itemKey { it.id }) { index ->
            movieList[index]?.let { movie ->
                DiscoverItem(movie, onMovieClicked)
            }
        }

        item {
            if (movieList.loadState.append is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}
