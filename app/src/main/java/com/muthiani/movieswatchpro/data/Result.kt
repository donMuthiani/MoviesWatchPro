package com.muthiani.movieswatchpro.data

import com.google.gson.annotations.SerializedName
import com.muthiani.movieswatchpro.data.Result.Dates

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
    val data: T? = null,
    val totalPages: Int = 1,
    val totalResults: Int = 1,
    val page: Int = 1,
    val dates: Dates? = null,
)

data class WatchListResponse(
    val success: Boolean,
    val status_code: Int,
    val status_message: String
)
