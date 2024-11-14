package com.muthiani.movieswatchpro.ui.discover

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.muthiani.movieswatchpro.models.MovieCollection
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun MovieCollectionItem(
    movieCollection: MovieCollection,
    modifier: Modifier = Modifier,
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
                onClick = { /* todo */ },
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
    LazyRow(modifier = modifier, contentPadding = PaddingValues(start = 24.dp)) {
        items(movieCollection.movies.take(15)) { movie ->
            DiscoverItem(movie, onMovieClicked)
        }
    }
}
