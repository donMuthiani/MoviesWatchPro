@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.muthiani.movieswatchpro.presentation.detail

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.muthiani.movieswatchpro.LocalNavAnimatedVisibilityScope
import com.muthiani.movieswatchpro.LocalSharedTransitionScope
import com.muthiani.movieswatchpro.R
import com.muthiani.movieswatchpro.data.remote.ApiConstants
import com.muthiani.movieswatchpro.domain.entity.MovieModel
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

val defaultPadding = Modifier.padding(8.dp)
val defaultPaddingTop = Modifier.padding(top = 8.dp)
val sectionPaddingHorizontal = Modifier.padding(horizontal = 24.dp)

@Composable
fun MovieDetailScreen(
    movieId: Long,
    upPress: () -> Unit,
    movieDetailViewModel: MovieDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(movieId) {
        movieDetailViewModel.loadMovieWatchlistStatus(movieId.toInt())
        movieDetailViewModel.getMovie(movieId.toInt())
    }

    val uiState by movieDetailViewModel.uiState.collectAsState()

    when (uiState) {
        is MovieDetailViewModel.MovieDetailUiState.Initial -> {}
        is MovieDetailViewModel.MovieDetailUiState.Loading -> {
            LoadingScreen()
        }

        is MovieDetailViewModel.MovieDetailUiState.Movie -> {
            MovieDetailContent(
                movieDetailViewModel,
                upPress,
                (uiState as MovieDetailViewModel.MovieDetailUiState.Movie).movieModel
            )
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
    movie: MovieModel
) {
    val isInWatchlist by movieDetailViewModel.isInWatchList.collectAsState()
    val isLoading by movieDetailViewModel.isWatchListLoaderActive.collectAsState()
    val sharedTransitionScope =
        LocalSharedTransitionScope.current ?: return
    val animatedVisibilityScope =
        LocalNavAnimatedVisibilityScope.current ?: return

    with(sharedTransitionScope) {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = MoviesWatchProTheme.colors.uiBackground)
                .verticalScroll(rememberScrollState())
                .semantics { contentDescription = "MovieDetailScreen" }
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
            MoviePosterSection(movie = movie, upPress = upPress)
            MovieInfoSection(movie = movie, isInWatchlist = isInWatchlist, isLoading = isLoading, onWatchListClicked = { movieDetailViewModel.addToWatchList(movie.id) })
            MovieOverviewSection(movie = movie)
            MovieRatingSection(movie = movie)
        }
    }
}

@Composable
fun MoviePosterSection(movie: MovieModel, upPress: () -> Unit) {
    Box(
        modifier =
        Modifier
            .fillMaxWidth()
            .height(250.dp),
    ) {
        AsyncImage(
            model = "${ApiConstants.BASE_URL}${movie.posterPath}",
            contentDescription = stringResource(R.string.movie_poster_path),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = stringResource(R.string.navigate_back),
            tint = Color.White,
            modifier = Modifier
                .statusBarsPadding()
                .size(36.dp)
                .background(Color.White.copy(alpha = 0.3f), shape = CircleShape)
                .clickable {
                    upPress.invoke()
                }
                .align(Alignment.TopStart)
                .semantics { contentDescription = "Back" }
                .then(defaultPadding),
        )
    }
}

@Composable
fun MovieInfoSection(
    movie: MovieModel,
    isInWatchlist: Boolean,
    isLoading: Boolean,
    onWatchListClicked: () -> Unit
) {
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
                .then(defaultPadding)
        ) {
            AsyncImage(
                model = "${ApiConstants.BASE_URL}${movie.backdropPath}",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier =
                Modifier
                    .width(120.dp)
                    .height(200.dp)
                    .padding(start = 16.dp)
                    .offset(y = ((-60).dp))
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.wrapContentHeight()) {
                Text(
                    text = movie.title.orEmpty(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MoviesWatchProTheme.colors.textInteractive,
                    modifier =
                    Modifier.then(defaultPaddingTop)
                )

                Text(
                    text = "${movie.releaseDate?.isMovieRunning()}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MoviesWatchProTheme.colors.textSecondary,
                    modifier =
                    Modifier
                        .then(defaultPaddingTop)
                )

                MoviesWatchButton(
                    shape = RoundedCornerShape(16.dp),
                    backgroundGradient = MoviesWatchProTheme.colors.interactiveSecondary,
                    onClick = onWatchListClicked,
                    modifier = Modifier.padding(top = 24.dp).testTag("Toggle watchlist"),
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Icon(
                            painter =
                            painterResource(
                                if (isInWatchlist) {
                                    R.drawable.ic_check
                                } else {
                                    R.drawable.round_add
                                },
                            ),
                            contentDescription = stringResource(R.string.start_icon),
                            modifier = Modifier.padding(end = 8.dp),
                        )

                        Text(
                            text = if (isInWatchlist) stringResource(R.string.remove_from_watch_list) else stringResource(R.string.add_to_watch_list),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun MovieOverviewSection(movie: MovieModel) {
    Text(
        text = movie.overview.orEmpty(),
        style = MaterialTheme.typography.bodyLarge,
        color = MoviesWatchProTheme.colors.textInteractive,
        modifier = Modifier.then(sectionPaddingHorizontal)
            .offset(y = (-60).dp)
    )
    MoviesWatchDivider(thickness = 2.dp)
}

@Composable
private fun MovieRatingSection(movie: MovieModel) {

    val brandColor = MoviesWatchProTheme.colors.brand
    val annotatedString = remember(movie.voteAverage) {
        buildAnnotatedString {
            append(movie.voteAverage.toString())
            append(
                AnnotatedString(
                    text = " · ",
                    spanStyle = SpanStyle(
                        color = brandColor,
                        fontSize = 36.sp
                    )
                )
            )
        }
    }

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

        Text(
            text = annotatedString,
            style = MaterialTheme.typography.titleLarge,
            color = MoviesWatchProTheme.colors.textInteractive,
            modifier =
            Modifier
                .align(Alignment.CenterVertically)
                .then(defaultPadding)
        )
    }
}
