package com.muthiani.movieswatchpro.domain.repository

import androidx.paging.PagingData
import com.muthiani.movieswatchpro.domain.entity.ManageWatchList
import com.muthiani.movieswatchpro.domain.entity.ManageWatchListResponse
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getWatchList(page: Int = 1): Flow<PagingData<MovieModel>>

    suspend fun getMovie(movieId: Int): MovieModel?

    fun getPopularMovies(page: Int = 1): Flow<PagingData<MovieModel>>

    fun getUpcomingMovies(page: Int = 1): Flow<PagingData<MovieModel>>

    fun getNowShowingMovies(page: Int = 1): Flow<PagingData<MovieModel>>

    fun getMoreMovies(page: Int = 1): Flow<PagingData<MovieModel>>

    suspend fun manageMovieWatchList(manageWatchList: ManageWatchList): ManageWatchListResponse
}
