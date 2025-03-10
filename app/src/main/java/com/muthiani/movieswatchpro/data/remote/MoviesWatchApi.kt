package com.muthiani.movieswatchpro.data.remote

import com.muthiani.movieswatchpro.domain.entity.ApiResponse
import com.muthiani.movieswatchpro.domain.entity.ManageWatchList
import com.muthiani.movieswatchpro.domain.entity.ManageWatchListResponse
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.entity.WatchListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesWatchApi {
    @GET("movie/now_playing")
    suspend fun getNowShowing(
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
    ): ApiResponse<List<MovieModel>>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
    ): ApiResponse<List<MovieModel>>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1",
    ): ApiResponse<List<MovieModel>>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language") language: String = "en-US",
        @Query("include_adult") includeAdult: Boolean = true,
        @Query("page") page: Int = 1,
    ): ApiResponse<List<MovieModel>>

    @GET("trending/movie/{time_window}")
    suspend fun getTrending(
        @Path("time_window") timeWindow: String = "week",
    ): ApiResponse<List<MovieModel>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): MovieModel

    @GET("movie/{category}")
    suspend fun getMovieCategory(
        @Path("category") category: String,
    ): ApiResponse<List<MovieModel>>

    @POST("account/{account_id}/watchlist")
    suspend fun manageWatchList(
        @Path("account_id") account_id: Int,
        @Body manageWatchList: ManageWatchList,
    ): ManageWatchListResponse

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchList(
        @Path("account_id") account_id: Int? = ApiConstants.ACCOUNT_ID,
        @Query("page") page: Int = 1,
        ): ApiResponse<List<MovieModel>>
}
