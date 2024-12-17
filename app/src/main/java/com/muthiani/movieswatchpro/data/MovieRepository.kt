package com.muthiani.movieswatchpro.data

import com.muthiani.movieswatchpro.models.ManageWatchList
import com.muthiani.movieswatchpro.models.MovieModel

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

interface MovieRepository {
    // Get watchList based on profile interests
    suspend fun getWatchList(): Result<List<Movie>>

    // Get my Shows list
    suspend fun getMyShows(): Result<List<Movie>>

    suspend fun getMovie(movieId: Int): MovieModel?

    suspend fun getNowShowingMovies(): ApiResponse<List<MovieModel>>

    suspend fun getPopularMovies(): ApiResponse<List<MovieModel>>

    suspend fun getTopRatedMovies(): ApiResponse<List<MovieModel>>

    suspend fun getUpcomingMovies(): ApiResponse<List<MovieModel>>

    suspend fun getTrendingMovies(): ApiResponse<List<MovieModel>>

    suspend fun getMovieCategory(category: String): ApiResponse<List<MovieModel>>

    suspend fun manageMovieWatchList(manageWatchList: ManageWatchList): WatchListResponse
}
