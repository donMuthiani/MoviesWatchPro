package com.muthiani.movieswatchpro.presentation.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import timber.log.Timber
import java.util.UUID

@Composable
fun MovieCollectionItem(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<MovieModel>,
    name: String = "",
    onMovieClicked: (Long) -> Unit,
    onMoreClicked: (String) -> Unit = {},
) {
    println("Recomposition: $name") // Log to check
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .padding(start = 24.dp)
                    .heightIn(min = 56.dp),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                color = MoviesWatchProTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier =
                    Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.Start),
            )
            IconButton(
                onClick = {
                    onMoreClicked(name)
                },
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                    tint = MoviesWatchProTheme.colors.brand,
                    contentDescription = null,
                )
            }
        }
        Movies(movies, onMovieClicked)
    }
}

@Composable
fun Movies(
    movies: LazyPagingItems<MovieModel>,
    onMovieClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(modifier = modifier, contentPadding = PaddingValues(start = 24.dp)) {
        val limitedMovies = movies.itemSnapshotList.take(15)
        items(items = limitedMovies, key = { movie -> movie?.id ?: UUID.randomUUID().toString() }) { movie ->
            movie?.let {
                DiscoverItem(movie, onMovieClicked)
            }
        }

        movies.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.padding(8.dp)) }
                }
                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator(modifier = Modifier.padding(8.dp)) }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = loadState.refresh as LoadState.Error
                    Timber.i("error ${e.error.message}")
                    item { Text("Error: ${e.error.localizedMessage}", modifier = Modifier.padding(16.dp)) }
                }
            }
        }
    }
}
