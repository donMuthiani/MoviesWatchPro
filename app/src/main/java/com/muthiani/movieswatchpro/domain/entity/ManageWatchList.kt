package com.muthiani.movieswatchpro.domain.entity

data class ManageWatchList(
    val media_type: String = "movie",
    val media_id: Int,
    val watchlist: Boolean,
)
