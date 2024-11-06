package com.muthiani.movieswatchpro.ui.discover

import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold
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
import com.muthiani.movieswatchpro.data.Movie
import com.muthiani.movieswatchpro.ui.components.SearcheableTopBar
import com.muthiani.movieswatchpro.ui.home.WatchListViewModel

@Composable
fun DiscoverScreen(onMovieSelected: (Long, String) -> Unit, modifier: Modifier) {
    val watchListViewModel: WatchListViewModel = hiltViewModel()
    val movies = watchListViewModel.uiState.collectAsState().value.watchList

    Scaffold(topBar = { SearcheableTopBar() }, content = { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            DiscoverContent(movies)
        }
    }, bottomBar = {})
}

@Composable
fun DiscoverContent(itemsList: List<Movie>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(bottom = 24.dp),
        content = {
            items(itemsList) { movie ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .wrapContentHeight()
                    ) {
                        AsyncImage(
                            model = movie.imageUrl, contentDescription = "",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Text(text = movie.rating.toString(), modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(color = Color.White.copy(alpha = 0.2f)), style = MaterialTheme.typography.bodyMedium,
                            color = Color.White)
                    }

                    Text(text = movie.title, modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally), style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        })
}
