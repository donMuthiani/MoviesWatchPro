package com.muthiani.movieswatchpro.data

import com.muthiani.movieswatchpro.models.ManageWatchList
import com.muthiani.movieswatchpro.models.MovieModel

interface MovieRepository {
    suspend fun getWatchList(): WatchListResponse<List<MovieModel>>

    suspend fun getMovie(movieId: Int): MovieModel?

    suspend fun getNowShowingMovies(): ApiResponse<List<MovieModel>>

    suspend fun getPopularMovies(page: Int = 1): ApiResponse<List<MovieModel>>

    suspend fun getTopRatedMovies(): ApiResponse<List<MovieModel>>

    suspend fun getUpcomingMovies(): ApiResponse<List<MovieModel>>

    suspend fun getTrendingMovies(): ApiResponse<List<MovieModel>>

    suspend fun getMovieCategory(category: String): ApiResponse<List<MovieModel>>

    suspend fun manageMovieWatchList(manageWatchList: ManageWatchList): ManageWatchListResponse

//    suspend fun getDiscoverMovies(manageWatchList: ManageWatchList): ManageWatchListResponse
}
