package com.muthiani.movieswatchpro.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class RemoteKeysDaoTest {
    private lateinit var remoteKeysDao: RemoteKeysDao
    private lateinit var db: MoviesWatchDatabase

    @Before
    fun createDatabase() {
        db =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                MoviesWatchDatabase::class.java,
            ).allowMainThreadQueries().build()
        remoteKeysDao = db.remoteKeysDao()
    }

    @After
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun insertAndRetrieveRemoteKeysWorksCorrectly() =
        runTest {
            val remoteKey = RemoteKeysEntity("movie", 2)
            remoteKeysDao.insertOrReplace(remoteKey)
            val retrievedRemoteKeys = remoteKeysDao.remoteKeyByQuery("movie")
            assert(retrievedRemoteKeys == remoteKey)
        }

    @Test
    fun insertAndRetrieveWatchListRemoteKeysWorksCorrectly() =
        runTest {
            val remoteWatchListKey = RemoteKeysWatchList("movie", 2)
            remoteKeysDao.insertOrReplaceWatchList(remoteWatchListKey)
            val retrievedRemoteKeys = remoteKeysDao.remoteWatchListKeyByQuery("movie")
            assert(retrievedRemoteKeys == remoteWatchListKey)
        }

    @Test
    fun clearAll_deletesAllRemoteKeys() =
        runTest {
            val remoteKey = RemoteKeysEntity("movie", 2)
            remoteKeysDao.insertOrReplace(remoteKey)
            remoteKeysDao.clearAll()
            val retrievedRemoteKeys = remoteKeysDao.remoteKeyByQuery("movie")
            assertNull(retrievedRemoteKeys)
        }

    @Test
    fun clearAllWatchListKeys_deletesAllWatchListRemoteKeys() =
        runTest {
            val remoteWatchListKey = RemoteKeysWatchList("movie", 2)
            remoteKeysDao.insertOrReplaceWatchList(remoteWatchListKey)
            remoteKeysDao.clearAllWatchListKeys()
            val retrievedRemoteKeys = remoteKeysDao.remoteWatchListKeyByQuery("movie")
            assertNull(retrievedRemoteKeys)
        }
}
