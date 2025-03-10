@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.presentation.detail

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.muthiani.movieswatchpro.LocalNavAnimatedVisibilityScope
import com.muthiani.movieswatchpro.LocalSharedTransitionScope
import com.muthiani.movieswatchpro.R
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.entity.ProductionCompanies
import com.muthiani.movieswatchpro.presentation.components.ErrorScreen
import com.muthiani.movieswatchpro.presentation.components.LoadingScreen
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchButton
import com.muthiani.movieswatchpro.presentation.components.MoviesWatchDivider
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import com.muthiani.movieswatchpro.presentation.utils.MovieSharedElementKey
import com.muthiani.movieswatchpro.presentation.utils.MovieSharedElementType
import com.muthiani.movieswatchpro.presentation.utils.isMovieRunning

fun <T> nonSpatialExpressiveSpring() =
    spring<T>(
        dampingRatio = 1f,
        stiffness = 1600f,
    )

fun <T> spatialExpressiveSpring() =
    spring<T>(
        dampingRatio = 0.8f,
        stiffness = 380f,
    )

@OptIn(ExperimentalSharedTransitionApi::class)
val movieDetailBoundsTransform =
    BoundsTransform { _, _ ->
        spatialExpressiveSpring()
    }

@Composable
@Preview
fun MovieDetailScreenPreview() {
    MovieDetailScreen(
        movieId = 3,
    ) {
    }
}

@Composable
fun MovieDetailScreen(
    movieId: Long,
    upPress: () -> Unit,
) {
    val movieDetailViewModel: MovieDetailViewModel = hiltViewModel()

    LaunchedEffect(movieId) {
        movieDetailViewModel.getMovie(movieId.toInt())
    }

    val uiState by movieDetailViewModel.uiState.collectAsState()

    when (uiState) {
        is MovieDetailViewModel.MovieDetailUiState.Initial -> {}
        is MovieDetailViewModel.MovieDetailUiState.Loading -> {
            LoadingScreen()
        }

        is MovieDetailViewModel.MovieDetailUiState.Movie -> {
            MovieDetailContent(movieDetailViewModel, upPress, (uiState as MovieDetailViewModel.MovieDetailUiState.Movie).movieModel)
        }

        is MovieDetailViewModel.MovieDetailUiState.Error -> {
            ErrorScreen(onDismiss = {
                upPress.invoke()
            }, errorMessage = (uiState as MovieDetailViewModel.MovieDetailUiState.Error).message)
        }
    }
}

@Composable
fun MovieDetailContent(
    movieDetailViewModel: MovieDetailViewModel,
    upPress: () -> Unit,
    movie: MovieModel,
) {
    val isLoading by movieDetailViewModel.isWatchListLoaderActive // Observe loader state
    val result by movieDetailViewModel.result
    var showDialog by remember { mutableStateOf(true) }
    val sharedTransitionScope = LocalSharedTransitionScope.current ?: throw IllegalArgumentException("No scope found")
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current ?: throw IllegalArgumentException("No scope found")

    result?.let {
        if (showDialog) {
            if (!it) {
                ErrorScreen(errorMessage = "Error adding to watchlist", onDismiss = { showDialog = false })
            }
        }
    }

    with(sharedTransitionScope) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = MoviesWatchProTheme.colors.uiBackground)
                    .verticalScroll(rememberScrollState())
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
                        .fillMaxWidth()
                        .height(250.dp),
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/original${movie.posterPath}",
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier =
                        Modifier
                            .fillMaxSize(),
                )
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Navigate back",
                    tint = Color.White,
                    modifier =
                        Modifier
                            .statusBarsPadding()
                            .padding(start = 8.dp)
                            .size(36.dp)
                            .background(Color.White.copy(alpha = 0.3f), shape = CircleShape)
                            .clickable {
                                upPress.invoke()
                            }
                            .align(Alignment.TopStart)
                            .padding(8.dp),
                )
            }

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 60.dp),
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp),
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/original${movie.backdropPath}",
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier =
                            Modifier
                                .width(120.dp)
                                .height(200.dp)
                                .padding(start = 16.dp)
                                .offset(y = ((-60).dp))
                                .clip(RoundedCornerShape(8.dp))
                                .sharedBounds(
                                    rememberSharedContentState(
                                        key =
                                            MovieSharedElementKey(
                                                snackId = movie.id.toLong(),
                                                type = MovieSharedElementType.Image,
                                            ),
                                    ),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                    clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(16.dp)),
                                    boundsTransform = movieDetailBoundsTransform,
                                    exit = fadeOut(nonSpatialExpressiveSpring()),
                                    enter = fadeIn(nonSpatialExpressiveSpring()),
                                ),
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.wrapContentHeight()) {
                        Text(
                            text = movie.title.orEmpty(),
                            style = MaterialTheme.typography.titleLarge,
                            color = MoviesWatchProTheme.colors.textInteractive,
                            modifier =
                                Modifier
                                    .padding(top = 8.dp),
                        )

                        Text(
                            text = "${movie.releaseDate?.isMovieRunning()}",
                            style = MaterialTheme.typography.titleSmall,
                            color = MoviesWatchProTheme.colors.textSecondary,
                            modifier =
                                Modifier
                                    .padding(top = 8.dp),
                        )

                        MoviesWatchButton(
                            shape = RoundedCornerShape(16.dp),
                            backgroundGradient = MoviesWatchProTheme.colors.interactiveSecondary,
                            onClick = { addToWatchList(movieDetailViewModel, movie.id) },
                            modifier = Modifier.padding(top = 24.dp),
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = Color.White, // You can change the color as needed
                                    strokeWidth = 2.dp,
                                )
                            } else {
                                Icon(
                                    painter =
                                        painterResource(
                                            if (result == true) {
                                                R.drawable.ic_check
                                            } else {
                                                R.drawable.round_add
                                            },
                                        ),
                                    // Replace with your icon
                                    contentDescription = "Start Icon",
                                    modifier = Modifier.padding(end = 8.dp),
                                )

                                Text(
                                    text = if (result == true) "Added to watchlist" else "Add to watchlist",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }

                Text(
                    text = movie.overview.orEmpty(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MoviesWatchProTheme.colors.textInteractive,
                    modifier =
                        Modifier
                            .padding(start = 24.dp)
                            .offset(y = (-60).dp),
                )

                MoviesWatchDivider(
                    thickness = 2.dp,
                )

                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "rating",
                        modifier =
                            Modifier
                                .size(24.dp)
                                .align(Alignment.CenterVertically),
                        tint = MoviesWatchProTheme.colors.brand,
                    )

                    val annotatedString =
                        buildAnnotatedString {
                            append(movie.voteAverage.toString())
                            append(
                                AnnotatedString(
                                    text = " · ",
                                    spanStyle =
                                        SpanStyle(
                                            color = MoviesWatchProTheme.colors.brand,
                                            fontSize = 36.sp,
                                        ),
                                ),
                            )
//                            append(movie.genreIds.first().toString())
                        }

                    Text(
                        text = annotatedString,
                        style = MaterialTheme.typography.titleLarge,
                        color = MoviesWatchProTheme.colors.textInteractive,
                        modifier =
                            Modifier
                                .align(Alignment.CenterVertically)
                                .padding(8.dp),
                    )
                }
//                Text(
//                    text = "Watch",
//                    style = MaterialTheme.typography.titleMedium,
//                    color = MoviesWatchProTheme.colors.textInteractive,
//                    modifier =
//                        Modifier
//                            .padding(start = 24.dp),
//                )

//                LazyRow(
//                    modifier = Modifier.padding(start = 16.dp),
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    contentPadding = PaddingValues(16.dp),
//                ) {
//                    itemsIndexed(movie.productionCompanies.orEmpty()) { _, provider ->
//                        MoviesWatchButton(
//                            backgroundGradient = listOf(MoviesWatchProTheme.colors.brand, MoviesWatchProTheme.colors.brand),
//                            onClick = { navigateToProvider(provider) },
//                            modifier =
//                                Modifier
//                                    .wrapContentSize(),
//                            shape = RoundedCornerShape(16.dp),
//                        ) {
//                            Text(
//                                text = provider.name.orEmpty(),
//                                style = MaterialTheme.typography.titleMedium,
//                                color = Color.White,
//                                textAlign = TextAlign.Center,
//                            )
//                        }
//                    }
//                }
            }
        }
    }
}

fun addToWatchList(
    movieDetailViewModel: MovieDetailViewModel,
    id: Int?,
) {
    if (id != null) {
        movieDetailViewModel.addToWatchList(id)
    }
}

fun navigateToProvider(provider: ProductionCompanies) {
}
