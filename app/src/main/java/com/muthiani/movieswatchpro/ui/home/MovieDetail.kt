package com.muthiani.movieswatchpro.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        movie?.let {
            MovieDetailScreen(movie = it, navController)
        }
    }
}

@Composable
@Preview
fun MovieDetailScreenPreview() {
    MovieDetailScreen(
        movie = Movie(
            id = 3,
            title = "Interstellar",
            description = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
            imageUrl = "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg",
            category = "SCI-FI",
            releaseDate = "November 2014",
            progress = "76",
            promoImage = "https://cdn.mos.cms.futurecdn.net/LVoJnXBbUH6xx9EkfgVnc5.jpg"
        ), navController = rememberNavController()
    )
}

@Composable
fun MovieDetailScreen(movie: Movie, navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Set desired height for the image
        ) {
            AsyncImage(
                model = movie.promoImage, contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Icon(imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Navigate back",
                tint = Color.White,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(start = 8.dp)
                    .size(36.dp)
                    .background(Color.White.copy(alpha = 0.3f), shape = CircleShape)
                    .clickable {
                        navController.popBackStack()
                    }
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 60.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = movie.imageUrl, contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(120.dp)
                        .height(200.dp)
                        .padding(start = 16.dp)
                        .offset(y = ((-60).dp))
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.wrapContentHeight()) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )

                    Text(
                        text = "movie.releaseDate.isMovieRunning()",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                CircularTextIndicator(
                    percentage = "${movie.progress}%",
                    backgroundColor = MaterialTheme.colorScheme.primary
                )
            }


            Text(
                text = movie.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(start = 24.dp)
                    .offset(y = (-60).dp)
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "rating",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically), tint = MaterialTheme.colorScheme.primary)

                val annotatedString = buildAnnotatedString {
                    append(movie.rating.toString())
                    append(
                        AnnotatedString(
                            text = ".",
                            spanStyle = SpanStyle(
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 26.sp
                            )
                        )
                    )
                    append(movie.category) }

                Text(text = annotatedString, style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.align(Alignment.CenterVertically))
            }

            val colors = listOf(Color.Red, Color.Blue, Color.Magenta)
            val itemColors = remember {
                List(movie.providers.size) { index -> colors[index % colors.size] }
            }
            LazyRow(modifier = Modifier.padding(start = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(movie.providers){ index, provider ->
                    val backgroundColor = itemColors[index] // Use precomputed color
                    Button(onClick = { navigateToPRovider(provider)},
                        modifier = Modifier
                            .height(50.dp) // Set desired height for the button
                            .width(120.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
                    ) {
                        Text(text = provider, style = MaterialTheme.typography.titleMedium, color = Color.White, textAlign = TextAlign.Center)
                    }
                }
            }


        }
    }
}

fun navigateToPRovider(provider: String) {
    
}

@Composable
fun CircularTextIndicator(percentage: String, backgroundColor: Color) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)

    ) {
        Text(
            text = percentage,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

