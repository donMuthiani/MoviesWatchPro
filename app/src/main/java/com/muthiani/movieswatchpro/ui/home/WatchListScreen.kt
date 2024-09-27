package com.muthiani.movieswatchpro.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.muthiani.movieswatchpro.data.Movie
import com.muthiani.movieswatchpro.ui.components.bottomPanel
import com.muthiani.movieswatchpro.ui.components.customHomeTopBar
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun WatchListScreen(
    navController: NavController,
    innerPadding: PaddingValues
) {

    val watchListViewModel: WatchListViewModel = hiltViewModel()
    val uiState by watchListViewModel.uiState.collectAsState()
    MoviesWatchProTheme {
        Scaffold(topBar = { customHomeTopBar() }, content = { innerpadding ->
            Column(modifier = Modifier.padding(innerpadding)) {
                when {
                    uiState.loading -> {
                        CircularProgressIndicator()
                    }

                    uiState.error?.isNotEmpty() == true -> {
                        Text(text = "Error: ${uiState.error}")
                    }

                    else -> {
                        ScrollContent(uiState.watchList, navController)
                    }
                }
            }
        }, bottomBar = {
            bottomPanel()
        })
    }
}

@Composable
fun ScrollContent(movieList: List<Movie>, navController: NavController) {
    LazyColumn {
        items(movieList) { movie ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { navController.navigate("movieDetail/${movie.id}") })
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = movie.imageUrl, contentDescription = "image from url",
                    modifier = Modifier
                        .height(120.dp)
                        .width(90.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = movie.title, style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    val annotatedString = buildAnnotatedString {
                        append(movie.releaseDate)
                        append(
                            AnnotatedString(
                                text = ".",
                                spanStyle = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 26.sp
                                )
                            )
                        )
                        append(movie.category)
                    }
                    Text(text = annotatedString, style = MaterialTheme.typography.bodyMedium)

                    LinearProgressIndicator(
                        progress = { (movie.progress.toFloat() / 100) }, modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun watchListScreenPreview() {
    WatchListScreen(
        navController = NavController(LocalContext.current),
        innerPadding = PaddingValues(12.dp, 12.dp)
    )
}
