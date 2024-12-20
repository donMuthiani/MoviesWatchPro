@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.ui.home

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.muthiani.movieswatchpro.LocalNavAnimatedVisibilityScope
import com.muthiani.movieswatchpro.LocalSharedTransitionScope
import com.muthiani.movieswatchpro.MovieSharedElementKey
import com.muthiani.movieswatchpro.MovieSharedElementType
import com.muthiani.movieswatchpro.data.Movie
import com.muthiani.movieswatchpro.ui.components.MoviesWatchButton
import com.muthiani.movieswatchpro.ui.components.MoviesWatchImage
import com.muthiani.movieswatchpro.ui.components.customHomeTopBar
import com.muthiani.movieswatchpro.ui.discover.DiscoverViewModel
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun WatchListScreen(onMovieSelected: (Long) -> Unit) {
    val watchListViewModel: WatchListViewModel = hiltViewModel()
    val discoverViewModel: DiscoverViewModel = hiltViewModel()

    val uiState by watchListViewModel.uiState.collectAsState()
    val discoverViewModelUiState by discoverViewModel.uiState.collectAsState()

    MoviesWatchProTheme {
        Scaffold(topBar = { customHomeTopBar(true) }, content = { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                when (discoverViewModelUiState) {
                    is DiscoverViewModel.DiscoverUiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    else -> {
                        MoviesWatchList(
                            uiState.watchList,
                            onMovieSelected,
                        )
                    }
                }
            }
        })
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MoviesWatchList(
    movieList: List<Movie>,
    onMovieClicked: (Long) -> Unit,
) {
    LazyColumn(
        modifier =
            Modifier
                .background(color = MoviesWatchProTheme.colors.uiBackground)
                .padding(bottom = 48.dp),
    ) {
        items(movieList) { movie ->
            MovieItem(movie, onMovieClicked)
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClicked: (Long) -> Unit,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current ?: throw IllegalArgumentException("No scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current ?: throw IllegalArgumentException("No scope found")
    with(sharedTransitionScope) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            onMovieClicked(movie.id.toLong())
                        },
                    )
                    .padding(vertical = 32.dp),
            verticalAlignment = Alignment.Top,
        ) {
            MoviesWatchImage(
                url = movie.imageUrl,
                contentDescription = "image from url",
                modifier =
                    Modifier
                        .height(120.dp)
                        .width(90.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .sharedBounds(
                            sharedContentState =
                                rememberSharedContentState(
                                    key =
                                        MovieSharedElementKey(
                                            snackId = movie.id.toLong(),
                                            type = MovieSharedElementType.Image,
                                        ),
                                ),
                            animatedVisibilityScope = animatedVisibilityScope,
                            exit = fadeOut(nonSpatialExpressiveSpring()),
                            enter = fadeIn(nonSpatialExpressiveSpring()),
                            boundsTransform = movieDetailBoundsTransform,
                        ),
            )
            Column(
                modifier =
                    Modifier
                        .padding(start = 16.dp),
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MoviesWatchProTheme.colors.textInteractive,
                )

                val annotatedString =
                    buildAnnotatedString {
                        append(movie.releaseDate)
                        append(
                            AnnotatedString(
                                text = ".",
                                spanStyle =
                                    SpanStyle(
                                        color = MoviesWatchProTheme.colors.brand,
                                        fontSize = 26.sp,
                                    ),
                            ),
                        )
                        append(movie.category)
                    }
                Text(
                    text = annotatedString,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MoviesWatchProTheme.colors.textInteractive,
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    LinearProgressIndicator(
                        color = MoviesWatchProTheme.colors.brand,
                        trackColor = MoviesWatchProTheme.colors.iconSecondary,
                        progress = { (movie.progress.toFloat() / 100) },
                        modifier =
                            Modifier
                                .height(10.dp)
                                .align(Alignment.CenterVertically)
                                .clip(shape = RoundedCornerShape(10.dp))
                                .weight(1f),
                    )

                    Text(
                        modifier =
                            Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp, end = 8.dp),
                        text = "3/8",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MoviesWatchProTheme.colors.textInteractive,
                    )
                }

                Row(Modifier.padding(top = 8.dp)) {
                    MoviesWatchButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        onClick = { /*TODO*/ },
                        border =
                            BorderStroke(
                                width = 1.dp,
                                color = MoviesWatchProTheme.colors.textInteractive,
                            ),
                        backgroundGradient = listOf(Color.Transparent, Color.Transparent),
                    ) {
                        Text(text = "Episode Info", style = MaterialTheme.typography.bodyLarge)
                    }

                    Text(
                        text = "5 Left",
                        style = (MaterialTheme.typography.bodyLarge),
                        modifier =
                            Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically),
                        color = MoviesWatchProTheme.colors.textInteractive.copy(alpha = 0.4f),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun watchListScreenPreview() {
//    WatchListScreen()
}
