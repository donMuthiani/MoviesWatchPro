package com.muthiani.movieswatchpro.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
import com.muthiani.movieswatchpro.data.local.RemoteKeysEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediatorTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: MoviesWatchDatabase
    private lateinit var api: MoviesWatchApi
    private lateinit var moviesRemoteMediator: MoviesRemoteMediator

    @Before
    fun setUp() {
        database =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                MoviesWatchDatabase::class.java,
            )
                .allowMainThreadQueries()
                .build()

        api = FakeMoviesWatchApi()
        moviesRemoteMediator = MoviesRemoteMediator(api, database, "popular")
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun refreshLoad_returnsSuccess() =
        runTest {
            val pagingState =
                PagingState<Int, MovieEntity>(
                    pages = emptyList(),
                    anchorPosition = null,
                    config = PagingConfig(20),
                    leadingPlaceholderCount = 0,
                )

            val result = moviesRemoteMediator.load(LoadType.REFRESH, pagingState)

            assertTrue(result is RemoteMediator.MediatorResult.Success)
        }

    @Test
    fun appendLoad_endOfPaginationReached() =
        runTest {
            database.remoteKeysDao().insertOrReplace(RemoteKeysEntity("movie", null)) // Simulate last page

            val pagingState =
                PagingState<Int, MovieEntity>(
                    pages = emptyList(),
                    anchorPosition = null,
                    config = PagingConfig(20),
                    leadingPlaceholderCount = 0,
                )

            val result = moviesRemoteMediator.load(LoadType.APPEND, pagingState)

            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
}
