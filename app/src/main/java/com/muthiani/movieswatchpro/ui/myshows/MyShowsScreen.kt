@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.ui.myshows

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.muthiani.movieswatchpro.LocalNavAnimatedVisibilityScope
import com.muthiani.movieswatchpro.LocalSharedTransitionScope
import com.muthiani.movieswatchpro.MovieSharedElementKey
import com.muthiani.movieswatchpro.MovieSharedElementType
import com.muthiani.movieswatchpro.ui.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.ui.components.customHomeTopBar
import com.muthiani.movieswatchpro.ui.home.WatchListViewModel
import com.muthiani.movieswatchpro.ui.home.movieDetailBoundsTransform
import com.muthiani.movieswatchpro.ui.home.nonSpatialExpressiveSpring
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun MyShowsScreen(onMovieSelected: (Long) -> Unit) {
    val watchListViewModel: WatchListViewModel = hiltViewModel()
    val myShows by watchListViewModel.uiState.collectAsState()

    val sharedTransitionScope = LocalSharedTransitionScope.current ?: throw IllegalArgumentException("No scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current ?: throw IllegalArgumentException("No scope found")

    with(sharedTransitionScope) {
        MoviesWatchScaffold(topBar = { customHomeTopBar(showActions = false) }, content = { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(myShows.watchList.filter { it.progress.toInt() > 50 }) { movie ->
                    Row(
                        modifier =
                            Modifier.padding(8.dp).fillMaxWidth().clickable {
                                onMovieSelected(movie.id.toLong())
                            },
                    ) {
                        AsyncImage(
                            model = movie.imageUrl,
                            contentDescription = "",
                            modifier =
                                Modifier
                                    .height(120.dp)
                                    .width(80.dp)
                                    .clip(RoundedCornerShape(4.dp))
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

                        Spacer(modifier = Modifier.size(16.dp))

                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MoviesWatchProTheme.colors.textInteractive,
                                modifier = Modifier.padding(top = 4.dp),
                            )
                        }
                    }
                }
            }
        }, bottomBar = {})
    }
}
