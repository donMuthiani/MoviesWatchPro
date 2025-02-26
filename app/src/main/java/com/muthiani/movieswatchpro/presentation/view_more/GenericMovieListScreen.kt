package com.muthiani.movieswatchpro.presentation.view_more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muthiani.movieswatchpro.presentation.components.ErrorScreen
import com.muthiani.movieswatchpro.presentation.components.LoadingScreen
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.presentation.components.customHomeTopBar
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import com.muthiani.movieswatchpro.presentation.utils.toCamelCase
import com.muthiani.movieswatchpro.presentation.watchlist.MoviesWatchList
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun GenericMovieListScreen(
    apiCallType: String,
    modifier: Modifier = Modifier,
    onMovieSelected: (Long) -> Unit,
    upPress: () -> Unit,
) {
    val movieListViewerViewModel: MovieListViewViewModel = hiltViewModel()
    val uiState = movieListViewerViewModel.uiState.collectAsState().value

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val today = dateFormat.format(Date())

    movieListViewerViewModel.getMovies(apiCallType = apiCallType, today = today)

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
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = MoviesWatchProTheme.colors.textInteractive,
                            modifier =
                                Modifier.size(24.dp)
                                    .clickable {
                                        upPress.invoke()
                                    },
                            // Adjust the icon size within the box
                        )
//                        Box(
//                            modifier =
//                                Modifier
//                                    .size(36.dp)
//                                    .background(MoviesWatchProTheme.colors.uiBackground, shape = CircleShape) // Apply background properly
//                                    .clickable { upPress.invoke() },
//                            contentAlignment = Alignment.Center,
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.ArrowBackIosNew,
//                                contentDescription = "Navigate back",
//                                tint = Color.White,
//                                modifier = Modifier.size(24.dp), // Adjust the icon size within the box
//                            )
//                        }

                        Text(
                            text = apiCallType.toCamelCase(),
                            style = MaterialTheme.typography.titleLarge,
                            color = MoviesWatchProTheme.colors.brand,
                            modifier =
                                Modifier
                                    .padding(start = 12.dp),
                        )
                    }

                    when (uiState) {
                        is MovieListViewViewModel.MovieListViewerUiState.Loading -> {
                            LoadingScreen()
                        }
                        is MovieListViewViewModel.MovieListViewerUiState.Initial -> {}
                        is MovieListViewViewModel.MovieListViewerUiState.Error -> {
                            ErrorScreen(onDismiss = {
                            }, errorMessage = uiState.message)
                        }
                        is MovieListViewViewModel.MovieListViewerUiState.Success -> {
                            Timber.i("Observing success ${uiState.collection}")
//                            val movies = (uiState as MovieListViewViewModel.MovieListViewerUiState.Success).collection.collectAsLazyPagingItems()
                            MoviesWatchList(
                                uiState.collection,
                                onMovieClicked = onMovieSelected,
                            )
                        }
                        else -> {
                        }
                    }
                }
            },
            bottomBar = {},
        )
    }
}
