package com.muthiani.movieswatchpro.data.remote

import com.muthiani.movieswatchpro.domain.entity.ApiResponse
import com.muthiani.movieswatchpro.domain.entity.ManageWatchList
import com.muthiani.movieswatchpro.domain.entity.ManageWatchListResponse
import com.muthiani.movieswatchpro.domain.entity.MovieModel

class FakeMoviesWatchApi : MoviesWatchApi {
    private val movies = mutableListOf<MovieModel>()
    private var shouldReturnError = false

    fun setShouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    fun addMovies(movieList: List<MovieModel>) {
        movies.addAll(movieList)
    }

    override suspend fun getNowShowing(
        language: String,
        includeAdult: Boolean,
        page: Int,
    ): ApiResponse<List<MovieModel>> {
        return if (shouldReturnError) {
            throw Exception("Test Exception")
        } else {
            ApiResponse(results = movies, page = page, total_pages = 10)
        }
    }

    override suspend fun getPopular(
        language: String,
        includeAdult: Boolean,
        page: Int,
    ): ApiResponse<List<MovieModel>> {
        return getNowShowing(language, includeAdult, page)
    }

    override suspend fun getTopRated(
        language: String,
        page: String,
    ): ApiResponse<List<MovieModel>> {
        return getNowShowing(language, false, page.toInt())
    }

    override suspend fun getUpcoming(
        language: String,
        includeAdult: Boolean,
        page: Int,
    ): ApiResponse<List<MovieModel>> {
        return getNowShowing(language, includeAdult, page)
    }

    override suspend fun getTrending(timeWindow: String): ApiResponse<List<MovieModel>> {
        return getNowShowing("en-US", false, 1)
    }

    override suspend fun getMovieDetail(movieId: Int): MovieModel {
        return movies.find { it.id == movieId } ?: throw Exception("Movie not found")
    }

    override suspend fun getMovieCategory(category: String): ApiResponse<List<MovieModel>> {
        return getNowShowing("en-US", false, 1)
    }

    override suspend fun manageWatchList(
        account_id: Int,
        manageWatchList: ManageWatchList,
    ): ManageWatchListResponse {
        return ManageWatchListResponse(success = true, status_code = 1, status_message = "Success")
    }

    override suspend fun getWatchList(
        account_id: Int?,
        page: Int,
    ): ApiResponse<List<MovieModel>> {
        return getNowShowing("en-US", false, page)
    }
}
