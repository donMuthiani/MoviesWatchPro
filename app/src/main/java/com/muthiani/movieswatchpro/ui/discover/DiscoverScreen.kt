@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.ui.discover

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.muthiani.movieswatchpro.models.MovieCollection
import com.muthiani.movieswatchpro.models.MovieModel
import com.muthiani.movieswatchpro.ui.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.ui.components.customHomeTopBar
import com.muthiani.movieswatchpro.ui.home.WatchListViewModel
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme
import timber.log.Timber

@Composable
fun DiscoverScreen(onMovieSelected: (Long) -> Unit) {
    val watchListViewModel: WatchListViewModel = hiltViewModel()
    val discoverViewModel: DiscoverViewModel = hiltViewModel()
    val uiState = discoverViewModel.uiState.collectAsState().value

    MoviesWatchScaffold(topBar = { customHomeTopBar(true) }, content = { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            DiscoverContent(uiState, onMovieSelected)
        }
    }, bottomBar = {})
}

@Composable
fun DiscoverContent(
    uiState: DiscoverViewModel.DiscoverUiState,
    onMovieSelected: (Long) -> Unit,
) {
    when (uiState) {
        is DiscoverViewModel.DiscoverUiState.Success -> {
            ContentList(uiState, onMovieSelected)
        }

        is DiscoverViewModel.DiscoverUiState.Loading -> {
        }

        is DiscoverViewModel.DiscoverUiState.Initial -> {
        }

        is DiscoverViewModel.DiscoverUiState.Error -> {
        }
    }
}

@Composable
fun ContentList(
    uiState: DiscoverViewModel.DiscoverUiState.Success,
    onMovieSelected: (Long) -> Unit,
) {
    LazyColumn(modifier = Modifier) {
        item {
            MovieCollectionItem(
                movieCollection =
                    MovieCollection(
                        name = "Now Showing",
                        movies = uiState.nowShowing,
                        onMovieClicked = onMovieSelected,
                    ),
            )
        }
    }
}

@Composable
fun DiscoverItem(
    movie: MovieModel,
    onMovieSelected: (Long) -> Unit,
) {
//    val sharedTransitionScope = LocalSharedTransitionScope.current ?: throw IllegalArgumentException("No scope found")
//    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current ?: throw IllegalArgumentException("No scope found")
//    with(sharedTransitionScope) {
    Timber.i("movie $movie")
    Column(
        modifier =
            Modifier
                .padding(16.dp)
                .width(160.dp)
                .clickable {
                    onMovieSelected(movie.id?.toLong() ?: 0)
                },
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .height(220.dp)
                    .wrapContentHeight(),
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original${movie.backdropPath}",
                contentDescription = "",
                modifier =
                    Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
//                        .sharedBounds(
//                            sharedContentState =
//                            rememberSharedContentState(
//                                key =
//                                MovieSharedElementKey(
//                                    snackId = movie.id?.toLong() ?: 0,
//                                    type = MovieSharedElementType.Image,
//                                ),
//                            ),
//                            animatedVisibilityScope = animatedVisibilityScope,
//                            exit = fadeOut(nonSpatialExpressiveSpring()),
//                            enter = fadeIn(nonSpatialExpressiveSpring()),
//                            boundsTransform = movieDetailBoundsTransform,
//                        ),
                contentScale = ContentScale.Crop,
            )

            Text(
                text = movie.voteAverage.toString(),
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .background(color = Color.Black.copy(alpha = 0.4f), shape = RoundedCornerShape(4.dp))
                        .padding(4.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MoviesWatchProTheme.colors.brand,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = movie.title ?: movie.originalTitle.orEmpty(),
            modifier =
                Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge,
            color = MoviesWatchProTheme.colors.textInteractive,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
//    }
}
