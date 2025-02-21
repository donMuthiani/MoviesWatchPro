package com.muthiani.movieswatchpro.data

import androidx.paging.PagingSource
import com.muthiani.movieswatchpro.models.ManageWatchList
import com.muthiani.movieswatchpro.models.MovieModel

interface MovieRepository {
    suspend fun getWatchList(): WatchListResponse<List<MovieModel>>

    suspend fun getMovie(movieId: Int): MovieModel?

    suspend fun getNowShowingMovies(page: Int = 1): ApiResponse<List<MovieModel>>

    suspend fun getPopularMovies(page: Int = 1): ApiResponse<List<MovieModel>>

    suspend fun getTopRatedMovies(): ApiResponse<List<MovieModel>>

    suspend fun getUpcomingMovies(page: Int = 1): ApiResponse<List<MovieModel>>

    suspend fun getTrendingMovies(): ApiResponse<List<MovieModel>>

    suspend fun getMovieCategory(category: String): ApiResponse<List<MovieModel>>

    suspend fun manageMovieWatchList(manageWatchList: ManageWatchList): ManageWatchListResponse

    suspend fun getDiscoverMovies(page: Int = 1): ApiResponse<List<MovieModel>>

    fun getPopularPagingSource(): PagingSource<Int, MovieModel>

    fun getNowShowingPagingSource(): PagingSource<Int, MovieModel>

    fun getUpcomingPagingSource(today: String): PagingSource<Int, MovieModel>
}
