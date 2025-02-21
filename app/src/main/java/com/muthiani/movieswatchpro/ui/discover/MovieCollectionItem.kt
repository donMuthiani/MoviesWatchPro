package com.muthiani.movieswatchpro.ui.discover

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.muthiani.movieswatchpro.models.MovieCollection
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun MovieCollectionItem(
    movieCollection: MovieCollection,
    modifier: Modifier = Modifier,
    onMoreClicked: (String) -> Unit = {},
) {
    Column(modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
                Modifier
                    .padding(start = 24.dp)
                    .heightIn(min = 56.dp),
        ) {
            Text(
                text = movieCollection.name,
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
                    onMoreClicked(movieCollection.name)
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
        Movies(movieCollection, movieCollection.onMovieClicked)
    }
}

@Composable
fun Movies(
    movieCollection: MovieCollection,
    onMovieClicked: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    val movies = movieCollection.movies.collectAsLazyPagingItems()

    LazyRow(modifier = modifier, contentPadding = PaddingValues(start = 24.dp)) {
        items(movies.itemCount) { index ->
            movies[index]?.let { movie ->
                DiscoverItem(movie, onMovieClicked)
            }
        }
    }
}
