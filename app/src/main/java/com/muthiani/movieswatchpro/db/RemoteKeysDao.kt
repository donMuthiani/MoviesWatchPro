package com.muthiani.movieswatchpro.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muthiani.movieswatchpro.data.pagination.RemoteKeys

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeys)

    @Query("SELECT * FROM remote_keys WHERE label = :query")
    suspend fun remoteKeyByQuery(query: String): RemoteKeys

    @Query("DELETE FROM remote_keys WHERE label = :query")
    suspend fun deleteByQuery(query: String)
}
