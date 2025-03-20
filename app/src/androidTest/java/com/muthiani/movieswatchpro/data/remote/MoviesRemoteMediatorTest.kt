package com.muthiani.movieswatchpro.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
import com.muthiani.movieswatchpro.data.local.RemoteKeysEntity
import com.muthiani.movieswatchpro.data.mapper.toMovieEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
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
    private lateinit var apiType: String

    @Before
    fun setUp() {
        apiType = "popular"
        database =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                MoviesWatchDatabase::class.java,
            )
                .allowMainThreadQueries()
                .build()

        api = FakeMoviesWatchApi()
        moviesRemoteMediator = MoviesRemoteMediator(api, database, apiType)
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

    @Test
    fun refreshLoad_insertsMoviesAndRemoteKeys() =
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

            val pagingSource =
                when (apiType) {
                    "popular" -> database.moviesDao().getPopularPagingSource()
                    "upcoming" -> database.moviesDao().getUpcomingPagingSource()
                    else -> database.moviesDao().getNowShowingPagingSource()
                }
            val loadedMovies =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                ) as PagingSource.LoadResult.Page

            assertTrue(loadedMovies.data.isNotEmpty())

            val remoteKeys = database.remoteKeysDao().remoteKeyByQuery("movie")
            assertTrue(remoteKeys?.nextKey != null)
        }

    @Test
    fun appendLoad_insertsMoreMoviesAndRemoteKeys() =
        runTest {
            val firstPage =
                when (apiType) {
                    "popular" -> api.getPopular(page = 1).results?.map { it.toMovieEntity() }
                    "upcoming" -> api.getUpcoming(page = 1).results?.map { it.toMovieEntity() }
                    else -> api.getNowShowing(page = 1).results?.map { it.toMovieEntity() }
                }
            database.moviesDao().insertMovies(firstPage.orEmpty())
            database.remoteKeysDao().insertOrReplace(RemoteKeysEntity("movie", 2))

            val pagingState =
                PagingState(
                    pages =
                        listOf(
                            PagingSource.LoadResult.Page(
                                data = firstPage.orEmpty(),
                                prevKey = null,
                                nextKey = 2,
                                itemsAfter = 0,
                            ),
                        ),
                    anchorPosition = 0,
                    config = PagingConfig(2),
                    leadingPlaceholderCount = 0,
                )

            val result = moviesRemoteMediator.load(LoadType.APPEND, pagingState)
            assertTrue(result is RemoteMediator.MediatorResult.Success)

            val pagingSource =
                when (apiType) {
                    "popular" -> database.moviesDao().getPopularPagingSource()
                    "upcoming" -> database.moviesDao().getUpcomingPagingSource()
                    else -> database.moviesDao().getNowShowingPagingSource()
                }
            val loadedMovies =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 4,
                        placeholdersEnabled = false,
                    ),
                ) as PagingSource.LoadResult.Page

            assertEquals(4, loadedMovies.data.size)

            val remoteKeys = database.remoteKeysDao().remoteKeyByQuery("movie")

            assertTrue(remoteKeys?.nextKey != null && (remoteKeys.nextKey ?: 0) > 2)
        }
}
