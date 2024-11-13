package com.muthiani.movieswatchpro.data

import com.muthiani.movieswatchpro.models.MovieModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesWatchApi {
    @GET("movie/now_playing")
    suspend fun getNowShowing(
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1",
    ): ApiResponse<List<MovieModel>>

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1",
    )

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1",
    )

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1",
    )
}
