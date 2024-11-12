@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.ui.discover

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.muthiani.movieswatchpro.LocalNavAnimatedVisibilityScope
import com.muthiani.movieswatchpro.LocalSharedTransitionScope
import com.muthiani.movieswatchpro.MovieSharedElementKey
import com.muthiani.movieswatchpro.MovieSharedElementType
import com.muthiani.movieswatchpro.data.Movie
import com.muthiani.movieswatchpro.ui.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.ui.components.SearcheableTopBar
import com.muthiani.movieswatchpro.ui.home.WatchListViewModel
import com.muthiani.movieswatchpro.ui.home.movieDetailBoundsTransform
import com.muthiani.movieswatchpro.ui.home.nonSpatialExpressiveSpring
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun DiscoverScreen(onMovieSelected: (Long) -> Unit) {
    val watchListViewModel: WatchListViewModel = hiltViewModel()
    val movies = watchListViewModel.uiState.collectAsState().value.watchList

    MoviesWatchScaffold(topBar = { SearcheableTopBar() }, content = { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            DiscoverContent(movies, onMovieSelected)
        }
    }, bottomBar = {})
}

@Composable
fun DiscoverContent(
    itemsList: List<Movie>,
    onMovieSelected: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(bottom = 24.dp),
        content = {
            items(itemsList) { movie ->
                DiscoverItem(movie, onMovieSelected)
            }
        },
    )
}

@Composable
fun DiscoverItem(
    movie: Movie,
    onMovieSelected: (Long) -> Unit,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current ?: throw IllegalArgumentException("No scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current ?: throw IllegalArgumentException("No scope found")
    with(sharedTransitionScope) {
        Column(
            modifier =
                Modifier.padding(16.dp).clickable {
                    onMovieSelected(movie.id.toLong())
                },
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .wrapContentHeight(),
            ) {
                AsyncImage(
                    model = movie.imageUrl,
                    contentDescription = "",
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
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
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = movie.rating.toString(),
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .background(color = Color.Black.copy(alpha = 0.4f), shape = RoundedCornerShape(4.dp))
                            .padding(4.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MoviesWatchProTheme.colors.brand,
                )
            }

            Text(
                text = movie.title,
                modifier =
                    Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyLarge,
                color = MoviesWatchProTheme.colors.textInteractive,
            )
        }
    }
}
