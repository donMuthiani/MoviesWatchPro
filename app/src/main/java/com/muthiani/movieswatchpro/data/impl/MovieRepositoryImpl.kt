package com.muthiani.movieswatchpro.data.impl

import com.muthiani.movieswatchpro.data.ApiResponse
import com.muthiani.movieswatchpro.data.ManageWatchListResponse
import com.muthiani.movieswatchpro.data.MovieRepository
import com.muthiani.movieswatchpro.data.MoviesWatchApi
import com.muthiani.movieswatchpro.data.WatchListResponse
import com.muthiani.movieswatchpro.models.ManageWatchList
import com.muthiani.movieswatchpro.models.MovieModel
import com.muthiani.movieswatchpro.utils.ConstantUtils
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(private val moviesWatchApi: MoviesWatchApi) : MovieRepository {
        override suspend fun getWatchList(): WatchListResponse<List<MovieModel>> {
            return moviesWatchApi.getWatchList(account_id = ConstantUtils.ACCOUNT_ID)
        }

        override suspend fun getMovie(movieId: Int): MovieModel {
            return moviesWatchApi.getMovieDetail(movieId)
        }

        override suspend fun getNowShowingMovies(): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getNowShowing()
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

        override suspend fun getUpcomingMovies(): ApiResponse<List<MovieModel>> {
            return moviesWatchApi.getUpcoming()
        }
    }
