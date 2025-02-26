package com.muthiani.movieswatchpro.domain.repository

import androidx.paging.PagingData
import com.muthiani.movieswatchpro.domain.entity.ApiResponse
import com.muthiani.movieswatchpro.domain.entity.ManageWatchList
import com.muthiani.movieswatchpro.domain.entity.ManageWatchListResponse
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.entity.WatchListResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getWatchList(): WatchListResponse<List<MovieModel>>

    suspend fun getMovie(movieId: Int): MovieModel?

    suspend fun getNowShowingMovies(page: Int = 1): ApiResponse<List<MovieModel>>

    fun getPopularMovies(page: Int = 1): Flow<PagingData<MovieModel>>

    suspend fun getTopRatedMovies(): ApiResponse<List<MovieModel>>

    suspend fun getUpcomingMovies(page: Int = 1): ApiResponse<List<MovieModel>>

    suspend fun getTrendingMovies(): ApiResponse<List<MovieModel>>

    suspend fun getMovieCategory(category: String): ApiResponse<List<MovieModel>>

    suspend fun manageMovieWatchList(manageWatchList: ManageWatchList): ManageWatchListResponse

    suspend fun getDiscoverMovies(page: Int = 1): ApiResponse<List<MovieModel>>

//    fun getPopularPagingSource(): PagingSource<Int, MovieModel>
//
//    fun getNowShowingPagingSource(): PagingSource<Int, MovieModel>
//
//    fun getUpcomingPagingSource(today: String): PagingSource<Int, MovieModel>
}
