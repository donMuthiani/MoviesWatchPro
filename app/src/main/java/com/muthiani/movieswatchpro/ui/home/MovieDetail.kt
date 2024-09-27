package com.muthiani.movieswatchpro.ui.home

import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
            MovieDetailScreen(movie = it)
        }
    }
}

@Composable
fun MovieDetailScreen(movie: Movie) {
    // TODO : Implement  movie detail
    Text(text = movie.title)
}
