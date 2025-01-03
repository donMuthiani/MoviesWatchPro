package com.muthiani.movieswatchpro.models

data class ManageWatchList(
    val media_type: String = "movie",
    val media_id: Int,
    val watchlist: Boolean,
)
