package com.muthiani.movieswatchpro.data.impl

import androidx.paging.PagingSource
import com.muthiani.movieswatchpro.data.ApiResponse
import com.muthiani.movieswatchpro.data.ManageWatchListResponse
import com.muthiani.movieswatchpro.data.MovieRepository
import com.muthiani.movieswatchpro.data.MoviesWatchApi
import com.muthiani.movieswatchpro.data.WatchListResponse
import com.muthiani.movieswatchpro.db.MoviesWatchDatabase
import com.muthiani.movieswatchpro.models.ManageWatchList
import com.muthiani.movieswatchpro.models.MovieModel
import com.muthiani.movieswatchpro.utils.ConstantUtils
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(private val moviesWatchApi: MoviesWatchApi, private val moviesWatchDatabase: MoviesWatchDatabase) : MovieRepository {
        override suspend fun getWatchList(): WatchListResponse<List<MovieModel>> {
            return moviesWatchApi.getWatchList(account_id = ConstantUtils.ACCOUNT_ID)
        }

        override suspend fun getMovie(movieId: Int): MovieModel {
            return moviesWatchApi.getMovieDetail(movieId)
        }

        override suspend fun getNowShowingMovies(page: Int): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getNowShowing(page = page)
        }

        override suspend fun getPopularMovies(page: Int): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getPopular(page.toString())
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
            return moviesWatchApi.manageWatchList(ConstantUtils.ACCOUNT_ID, manageWatchList)
        }

        override suspend fun getDiscoverMovies(page: Int): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getDiscoverMovies(page = page)
        }

        override fun getPopularPagingSource(): PagingSource<Int, MovieModel> {
            return moviesWatchDatabase.moviesDao().getPopularPagingSource()
        }

        override suspend fun getUpcomingMovies(page: Int): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getUpcoming(page = page)
        }

        override fun getNowShowingPagingSource(): PagingSource<Int, MovieModel> {
            return moviesWatchDatabase.moviesDao().getNowShowingPagingSource()
        }

        override fun getUpcomingPagingSource(today: String): PagingSource<Int, MovieModel> {
            return moviesWatchDatabase.moviesDao().getUpcomingPagingSource(today)
        }
    }
