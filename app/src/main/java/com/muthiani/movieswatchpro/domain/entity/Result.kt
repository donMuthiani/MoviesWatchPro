package com.muthiani.movieswatchpro.domain.entity

import com.google.gson.annotations.SerializedName
import com.muthiani.movieswatchpro.domain.entity.Result.Dates

sealed class Result<T> {
    data class Success<T>(
        val data: T,
        val totalPages: Int = 1,
        val totalResults: Int = 1,
        val page: Int = 1,
        val dates: Dates? = null,
    ) : Result<T>()

    data class Error(val exception: Exception) : Result<Nothing>()

    data class Dates(val maximum: String, val minimum: String)
}

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

data class ApiResponse<T>(
    @SerializedName("results")
    val results: T? = null,
    val data: T? = null,
    val total_pages: Int = 1,
    val total_results: Int = 1,
    val page: Int = 1,
    val dates: Dates? = null,
)

data class WatchListResponse<T>(
    @SerializedName("results")
    val results: T? = null,
    val total_pages: Int = 1,
    val total_results: Int = 1,
    val page: Int = 1,
)

data class ManageWatchListResponse(
    val success: Boolean,
    val status_code: Int,
    val status_message: String,
)

enum class ApiCallType {
    POPULAR,
    NOW_SHOWING,
    UPCOMING,
}
