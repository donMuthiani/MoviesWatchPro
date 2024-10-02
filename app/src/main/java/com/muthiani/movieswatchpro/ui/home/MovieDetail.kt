package com.muthiani.movieswatchpro.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.muthiani.movieswatchpro.data.Movie


@Composable
fun MovieDetail(navController: NavController, movieId: Int) {

    var movie by remember { mutableStateOf<Movie?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val watchListViewModel: WatchListViewModel = hiltViewModel()

    LaunchedEffect(movieId) {
        movie = watchListViewModel.getMovie(movieId)
        isLoading = false
    }

    if(isLoading) {
        CircularProgressIndicator()
    } else {
        movie?.let {
            MovieDetailScreen(movie = it, navController)
        }
    }
}

@Composable
fun MovieDetailScreen(movie: Movie, navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Set desired height for the image
    ) {
        AsyncImage(model = movie.imageUrl, contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize())

        Icon(imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "Navigate back",
            tint = Color.White,
            modifier = Modifier
                .statusBarsPadding()
                .padding(top = 8.dp, start = 8.dp)
                .size(36.dp)
                .background(Color.White.copy(alpha = 0.7f), shape = CircleShape)
                .clickable {
                    navController.popBackStack()
                }
                .align(Alignment.TopStart)
                .padding(8.dp)
        )
    }
}

