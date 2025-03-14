package com.muthiani.movieswatchpro.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey
    val id: String,
    val nextKey: Int?,
)

@Entity(tableName = "remote_keys_watchList")
data class RemoteKeysWatchList(
    @PrimaryKey
    val id: String,
    val nextKey: Int?,
)
