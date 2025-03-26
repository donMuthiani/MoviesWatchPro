package com.muthiani.movieswatchpro.data.repository

import androidx.paging.Pager
import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MovieEntityWatchList
import com.muthiani.movieswatchpro.data.remote.ApiConstants
import com.muthiani.movieswatchpro.data.remote.MoviesWatchApi
import com.muthiani.movieswatchpro.domain.entity.ApiResponse
import com.muthiani.movieswatchpro.domain.entity.ManageWatchList
import com.muthiani.movieswatchpro.domain.entity.ManageWatchListResponse
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {
    @InjectMocks
    private lateinit var movieRepositoryImpl: MovieRepositoryImpl

    @Mock
    private lateinit var popularMoviesPager: Pager<Int, MovieEntity>

    @Mock
    private lateinit var upcomingMoviesPager: Pager<Int, MovieEntity>

    @Mock
    private lateinit var nowShowingMoviesPager: Pager<Int, MovieEntity>

    @Mock
    private lateinit var viewMoreMoviesPager: Pager<Int, MovieEntity>

    @Mock
    private lateinit var watchListMoviesPager: Pager<Int, MovieEntityWatchList>

    @get:Rule
    val coroutinesTestRule = TestCoroutineRule()

    @Mock
    private lateinit var moviesWatchApi: MoviesWatchApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        movieRepositoryImpl =
            MovieRepositoryImpl(
                popularMoviesPager = popularMoviesPager,
                upcomingMoviesPager = upcomingMoviesPager,
                nowShowingMoviesPager = nowShowingMoviesPager,
                viewMoreMoviesPager = viewMoreMoviesPager,
                watchListMoviesPager = watchListMoviesPager,
                moviesWatchApi = moviesWatchApi,
            )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test getWatchList API call returns expected movies`() {
        runTest {
            val expectedMovies =
                listOf(
                    MovieModel(id = 1, title = "Movie 1"),
                    MovieModel(id = 2, title = "Movie 2"),
                )
            val mockResponse = ApiResponse(data = expectedMovies)

            `when`(moviesWatchApi.getWatchList(ApiConstants.ACCOUNT_ID, 1)).thenReturn(mockResponse)

            val result = moviesWatchApi.getWatchList(ApiConstants.ACCOUNT_ID, 1)
            assertEquals(expectedMovies, result.data)
        }
    }

    @Test
    fun `test manageMovieWatchList sends correct request`() =
        runTest {
            val manageWatchList = ManageWatchList(media_id = 123, watchlist = true)
            val expectedResponse = ManageWatchListResponse(success = true, status_code = 200, status_message = "Success")

            `when`(moviesWatchApi.manageWatchList(ApiConstants.ACCOUNT_ID, manageWatchList)).thenReturn(expectedResponse)

            val result = movieRepositoryImpl.manageMovieWatchList(manageWatchList)

            // Then
            assertEquals(expectedResponse, result)
            verify(moviesWatchApi).manageWatchList(ApiConstants.ACCOUNT_ID, manageWatchList)
        }

    @Test
    fun `test getMovieDetail fetches correct data`() =
        runTest {
            val movieId = 1234
            val expectedMovie = MovieModel(id = movieId, title = "Test Movie")

            `when`(moviesWatchApi.getMovieDetail(movieId)).thenReturn(expectedMovie)
            val result = movieRepositoryImpl.getMovie(movieId)

            assert(result == expectedMovie)
            verify(moviesWatchApi).getMovieDetail(movieId)
        }
}
