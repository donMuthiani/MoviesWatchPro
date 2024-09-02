package com.muthiani.movieswatchpro.data

data class Movie(val title: String, val description: String, val imageUrl: String, val category: String, val releaseDate: String, val progress: String)

interface FakeWatchListRepository {
    // Get watchList based on profile interests
    suspend fun getWatchList(): Result<List<Movie>>

    // Get my Shows list

    suspend fun getMyShows(): Result<List<Movie>>
}
