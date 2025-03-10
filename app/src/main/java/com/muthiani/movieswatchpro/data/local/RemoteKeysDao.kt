package com.muthiani.movieswatchpro.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeysEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplaceWatchList(remoteKey: RemoteKeysWatchList)

    @Query("SELECT * FROM remote_keys WHERE id = :query")
    suspend fun remoteKeyByQuery(query: String): RemoteKeysEntity

    @Query("SELECT * FROM remote_keys_watchList WHERE id = :query")
    suspend fun remoteWatchListKeyByQuery(query: String): RemoteKeysWatchList

    @Query("DELETE FROM remote_keys WHERE id = :query")
    suspend fun deleteByQuery(query: String)

    @Query("DELETE FROM remote_keys_watchList WHERE id = :query")
    suspend fun deleteByQueryWatchList(query: String)

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()

    @Query("DELETE FROM remote_keys_watchList")
    suspend fun clearAllWatchListKeys()
}
