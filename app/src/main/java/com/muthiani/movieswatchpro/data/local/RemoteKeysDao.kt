package com.muthiani.movieswatchpro.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeysEntity)

    @Query("SELECT * FROM remote_keys WHERE id = :query")
    suspend fun remoteKeyByQuery(query: String): RemoteKeysEntity

    @Query("DELETE FROM remote_keys WHERE id = :query")
    suspend fun deleteByQuery(query: String)

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}
