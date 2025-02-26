@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.presentation.discover

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.muthiani.movieswatchpro.LocalNavAnimatedVisibilityScope
import com.muthiani.movieswatchpro.LocalSharedTransitionScope
import com.muthiani.movieswatchpro.R
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.presentation.detail.movieDetailBoundsTransform
import com.muthiani.movieswatchpro.presentation.detail.nonSpatialExpressiveSpring
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import com.muthiani.movieswatchpro.presentation.utils.MovieSharedElementKey
import com.muthiani.movieswatchpro.presentation.utils.MovieSharedElementType
import timber.log.Timber

@Composable
fun DiscoverItem(
    movie: MovieModel,
    onMovieSelected: (Long) -> Unit,
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current ?: throw IllegalArgumentException("No scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current ?: throw IllegalArgumentException("No scope found")
    with(sharedTransitionScope) {
        Column(
            modifier =
                Modifier
                    .padding(16.dp)
                    .width(120.dp)
                    .clickable {
                        Timber.i("Movie selected: ${movie.title}")
                        onMovieSelected(movie.id.toLong())
                    }
                    .sharedBounds(
                        rememberSharedContentState(
                            key =
                                MovieSharedElementKey(
                                    snackId = movie.id.toLong(),
                                    type = MovieSharedElementType.Bounds,
                                ),
                        ),
                        animatedVisibilityScope = animatedVisibilityScope,
                        clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(16.dp)),
                        boundsTransform = movieDetailBoundsTransform,
                        exit = fadeOut(nonSpatialExpressiveSpring()),
                        enter = fadeIn(nonSpatialExpressiveSpring()),
                    ),
        ) {
            Box(
                modifier =
                    Modifier
                        .wrapContentHeight(),
            ) {
                AsyncImage(
                    model =
                        ImageRequest.Builder(LocalContext.current)
                            .data("https://image.tmdb.org/t/p/original${movie.posterPath}")
                            .crossfade(true)
                            .placeholder(R.drawable.movie_placeholder)
                            .error(R.drawable.movie_error_placeholder)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build(),
                    contentDescription = "",
                    modifier =
                        Modifier
                            .height(180.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .sharedBounds(
                                sharedContentState =
                                    rememberSharedContentState(
                                        key =
                                            MovieSharedElementKey(
                                                snackId = movie.id?.toLong() ?: 0,
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
                    text = (movie.voteAverage.toString()),
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .background(color = Color.Black.copy(alpha = 0.4f), shape = RoundedCornerShape(4.dp))
                            .padding(4.dp),
                    style = MaterialTheme.typography.titleMedium,
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
    }
}
