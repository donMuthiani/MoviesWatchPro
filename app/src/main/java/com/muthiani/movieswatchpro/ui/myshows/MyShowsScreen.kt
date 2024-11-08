package com.muthiani.movieswatchpro.ui.myshows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.muthiani.movieswatchpro.ui.components.MoviesWatchScaffold
import com.muthiani.movieswatchpro.ui.components.customHomeTopBar
import com.muthiani.movieswatchpro.ui.home.WatchListViewModel
import com.muthiani.movieswatchpro.ui.theme.MoviesWatchProTheme

@Composable
fun MyShowsScreen(onMovieSelected: (Long, String) -> Unit, modifier: Modifier) {

    val watchListViewModel: WatchListViewModel = hiltViewModel()
    val myShows by watchListViewModel.uiState.collectAsState()

    MoviesWatchScaffold(topBar = { customHomeTopBar(showActions = false) }, content = { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(myShows.watchList.filter { it.progress.toInt() > 50 }) { movie ->
                Row(modifier = Modifier.padding(8.dp)) {
                    AsyncImage(model = movie.imageUrl, contentDescription = "",
                        modifier = Modifier
                            .height(120.dp)
                            .width(80.dp)
                            .clip(RoundedCornerShape(4.dp)))

                    Spacer(modifier = Modifier.size(16.dp))

                    Column {
                        Text(text = movie.title, style = MaterialTheme.typography.titleMedium,
                            color = MoviesWatchProTheme.colors.textInteractive,
                            modifier = Modifier.padding(top = 4.dp))
                    }
                }
            }
        }
    }, bottomBar = {})
}
