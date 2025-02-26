package com.muthiani.movieswatchpro.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
import com.muthiani.movieswatchpro.data.mapper.toMovieModel
import com.muthiani.movieswatchpro.data.remote.ApiConstants
import com.muthiani.movieswatchpro.data.remote.MoviesWatchApi
import com.muthiani.movieswatchpro.domain.entity.ApiResponse
import com.muthiani.movieswatchpro.domain.entity.ManageWatchList
import com.muthiani.movieswatchpro.domain.entity.ManageWatchListResponse
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.entity.WatchListResponse
import com.muthiani.movieswatchpro.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(
        private val moviesPager: Pager<Int, MovieEntity>,
        private val moviesWatchApi: MoviesWatchApi,
        private val moviesWatchDatabase: MoviesWatchDatabase,
    ) : MovieRepository {
        override suspend fun getWatchList(): WatchListResponse<List<MovieModel>> {
            return moviesWatchApi.getWatchList(account_id = ApiConstants.ACCOUNT_ID)
        }

        override suspend fun getMovie(movieId: Int): MovieModel {
            return moviesWatchApi.getMovieDetail(movieId)
        }

        override suspend fun getNowShowingMovies(page: Int): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getNowShowing(page = page)
        }

        override fun getPopularMovies(page: Int): Flow<PagingData<MovieModel>> {
            return moviesPager.flow.map { pagingData ->
                pagingData.map { it.toMovieModel() }
            }
        }

        override suspend fun getTopRatedMovies(): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getTopRated()
        }

        override suspend fun getTrendingMovies(): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getTrending(timeWindow = "week")
        }

        override suspend fun getMovieCategory(category: String): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getMovieCategory(category)
        }

        override suspend fun manageMovieWatchList(manageWatchList: ManageWatchList): ManageWatchListResponse {
            return moviesWatchApi.manageWatchList(ApiConstants.ACCOUNT_ID, manageWatchList)
        }

        override suspend fun getDiscoverMovies(page: Int): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getDiscoverMovies(page = page)
        }

//        override fun getPopularPagingSource(): PagingSource<Int, MovieModel> {
//            return moviesWatchDatabase.moviesDao().getPopularPagingSource()
//        }

        override suspend fun getUpcomingMovies(page: Int): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getUpcoming(page = page)
        }

//        override fun getNowShowingPagingSource(): PagingSource<Int, MovieModel> {
//            return moviesWatchDatabase.moviesDao().getNowShowingPagingSource()
//        }
//
//        override fun getUpcomingPagingSource(today: String): PagingSource<Int, MovieModel> {
//            return moviesWatchDatabase.moviesDao().getUpcomingPagingSource(today)
//        }
    }
