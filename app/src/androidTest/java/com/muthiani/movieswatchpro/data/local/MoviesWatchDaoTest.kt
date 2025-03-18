package com.muthiani.movieswatchpro.data.local

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MoviesWatchDaoTest {
    private lateinit var dao: MoviesWatchDao
    private lateinit var db: MoviesWatchDatabase

    @Before
    fun createDatabase() {
        db =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                MoviesWatchDatabase::class.java,
            ).allowMainThreadQueries().build()
        dao = db.moviesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        db.close()
    }

    @Test
    fun insertMoviesAndPopularPagingSourceReturnsCorrectData() {
        runTest {
            // Given
            val movies =
                listOf(
                    MovieEntity(1, false, "/backdrop1.jpg", listOf(1, 2), "en", "Original Title 1", "Overview 1", 10.0, "/poster1.jpg", "2023-01-01", "Title 1", false, 8.0, 100),
                    MovieEntity(2, true, "/backdrop2.jpg", listOf(3, 4), "es", "Original Title 2", "Overview 2", 12.0, "/poster2.jpg", "2023-02-01", "Title 2", true, 9.0, 200),
                )

            // When
            dao.insertMovies(movies)
            val pagingSource = dao.getPopularPagingSource()
            val loadResult =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            // Then
            assertTrue(loadResult is PagingSource.LoadResult.Page)
            val loadedData = (loadResult as PagingSource.LoadResult.Page).data
            assertEquals(2, loadedData.size)
            assertEquals(movies[0].id, loadedData[1].id)
            assertEquals(movies[1].id, loadedData[0].id)
        }
    }

    @Test
    fun clearAllMoviesWorksCorrectly() {
        runTest {
            // Given
            val movies =
                listOf(
                    MovieEntity(1, false, "/backdrop1.jpg", listOf(1, 2), "en", "Original Title 1", "Overview 1", 10.0, "/poster1.jpg", "2023-01-01", "Title 1", false, 8.0, 100),
                    MovieEntity(2, true, "/backdrop2.jpg", listOf(3, 4), "es", "Original Title 2", "Overview 2", 12.0, "/poster2.jpg", "2023-02-01", "Title 2", true, 9.0, 200),
                )
            dao.insertMovies(movies)

            // When
            dao.clearAll()
            val pagingSource = dao.getPopularPagingSource()
            val loadResult =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            // Then
            assertTrue(loadResult is PagingSource.LoadResult.Page)
            val loadedData = (loadResult as PagingSource.LoadResult.Page).data
            assertTrue(loadedData.isEmpty())
        }
    }

    @Test
    fun insertMoviesAndViewMorePagingSourceReturnsCorrectData() {
        runTest {
            // Given
            val movies =
                listOf(
                    MovieEntity(1, false, "/backdrop1.jpg", listOf(1, 2), "en", "Original Title 1", "Overview 1", 10.0, "/poster1.jpg", "2023-01-01", "Title 1", false, 8.0, 100),
                    MovieEntity(2, true, "/backdrop2.jpg", listOf(3, 4), "es", "Original Title 2", "Overview 2", 12.0, "/poster2.jpg", "2023-02-01", "Title 2", true, 9.0, 200),
                )

            // When
            dao.insertMovies(movies)
            val pagingSource = dao.pagingSource()
            val loadResult =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            // Then
            assertTrue(loadResult is PagingSource.LoadResult.Page)
            val loadedData = (loadResult as PagingSource.LoadResult.Page).data
            assertEquals(2, loadedData.size)
            assertEquals(movies[0].id, loadedData[0].id)
            assertEquals(movies[1].id, loadedData[1].id)
        }
    }

    @Test
    fun insertWatchListMoviesAndWatchListPagingSourceReturnsCorrectData() {
        runTest {
            // Given
            val movies =
                listOf(
                    MovieEntityWatchList(1, false, "/backdrop1.jpg", listOf(1, 2), "en", "Original Title 1", "Overview 1", 10.0, "/poster1.jpg", "2023-01-01", "Title 1", false, 8.0, 100),
                    MovieEntityWatchList(2, true, "/backdrop2.jpg", listOf(3, 4), "es", "Original Title 2", "Overview 2", 12.0, "/poster2.jpg", "2023-02-01", "Title 2", true, 9.0, 200),
                )

            // When
            dao.insertWatchListMovies(movies)
            val pagingSource = dao.watchLisPagingSource()
            val loadResult =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            // Then
            assertTrue(loadResult is PagingSource.LoadResult.Page)
            val loadedData = (loadResult as PagingSource.LoadResult.Page).data
            assertEquals(2, loadedData.size)
            assertEquals(movies[0].id, loadedData[0].id)
            assertEquals(movies[1].id, loadedData[1].id)
        }
    }

    @Test
    fun clearAllWatchListWorksCorrectly() {
        runTest {
            // Given
            val movies =
                listOf(
                    MovieEntityWatchList(1, false, "/backdrop1.jpg", listOf(1, 2), "en", "Original Title 1", "Overview 1", 10.0, "/poster1.jpg", "2023-01-01", "Title 1", false, 8.0, 100),
                    MovieEntityWatchList(2, true, "/backdrop2.jpg", listOf(3, 4), "es", "Original Title 2", "Overview 2", 12.0, "/poster2.jpg", "2023-02-01", "Title 2", true, 9.0, 200),
                )
            dao.insertWatchListMovies(movies)

            // When
            dao.clearAllWatchList()
            val pagingSource = dao.watchLisPagingSource()
            val loadResult =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            // Then
            assertTrue(loadResult is PagingSource.LoadResult.Page)
            val loadedData = (loadResult as PagingSource.LoadResult.Page).data
            assertTrue(loadedData.isEmpty())
        }
    }

    @Test
    fun insertMoviesAndUpcomingPagingSourceReturnsCorrectData() {
        runTest {
            // Given
            val movies =
                listOf(
                    MovieEntity(1, false, "/backdrop1.jpg", listOf(1, 2), "en", "Original Title 1", "Overview 1", 10.0, "/poster1.jpg", "2025-04-04", "Title 1", false, 8.0, 100),
                    MovieEntity(2, true, "/backdrop2.jpg", listOf(3, 4), "es", "Original Title 2", "Overview 2", 12.0, "/poster2.jpg", "2025-04-10", "Title 2", true, 9.0, 200),
                )

            // When
            dao.insertMovies(movies)
            val pagingSource = dao.getUpcomingPagingSource()
            val loadResult =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            // Then
            assertTrue(loadResult is PagingSource.LoadResult.Page)
            val loadedData = (loadResult as PagingSource.LoadResult.Page).data
            assertEquals(2, loadedData.size)
            assertEquals(movies[0].id, loadedData[0].id)
            assertEquals(movies[1].id, loadedData[1].id)
        }
    }

    @Test
    fun insertMoviesAndNowShowingPagingSourceReturnsCorrectData() {
        runTest {
            // Given
            val movies =
                listOf(
                    MovieEntity(1, false, "/backdrop1.jpg", listOf(1, 2), "en", "Original Title 1", "Overview 1", 10.0, "/poster1.jpg", "2025-04-04", "Title 1", false, 8.0, 100),
                    MovieEntity(2, true, "/backdrop2.jpg", listOf(3, 4), "es", "Original Title 2", "Overview 2", 12.0, "/poster2.jpg", "2025-04-10", "Title 2", true, 9.0, 200),
                )

            // When
            dao.insertMovies(movies)
            val pagingSource = dao.getNowShowingPagingSource()
            val loadResult =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            // Then
            assertTrue(loadResult is PagingSource.LoadResult.Page)
            val loadedData = (loadResult as PagingSource.LoadResult.Page).data
            assertEquals(2, loadedData.size)
            assertEquals(movies[0].id, loadedData[1].id)
            assertEquals(movies[1].id, loadedData[0].id)
        }
    }
}
