package com.muthiani.movieswatchpro.data

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
    val category: String,
    val releaseDate: String,
    val progress: String,
    val promoImage: String = "",
    val rating: Float = 8.1f,
    val providers: List<String> = listOf("Netflix", "Hulu", "Disney+", "HBO"),
)

interface FakeWatchListRepository {
    // Get watchList based on profile interests
    suspend fun getWatchList(): Result<List<Movie>>

    // Get my Shows list
    suspend fun getMyShows(): Result<List<Movie>>

    suspend fun getMovie(movieId: Int): Movie?
}
