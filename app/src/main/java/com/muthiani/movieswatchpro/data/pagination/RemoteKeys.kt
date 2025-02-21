package com.muthiani.movieswatchpro.data.pagination

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val label: String,
    val nextKey: Int?,
)
