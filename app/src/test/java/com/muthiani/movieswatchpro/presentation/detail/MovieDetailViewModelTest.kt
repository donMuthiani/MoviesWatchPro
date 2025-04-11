package com.muthiani.movieswatchpro.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.muthiani.movieswatchpro.domain.entity.ManageWatchList
import com.muthiani.movieswatchpro.domain.entity.ManageWatchListResponse
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieDetailViewModel(movieRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovie should update uiState to movie when repository returns valid movie`() = runTest {
        val movieId = 123
        val movieModel = MovieModel(
            false,
            "",
            listOf(),
            id = movieId,
            "title",
            "overview",
            "posterPath",
            5.5,
            "releaseDate",
            "voteAverage"
        )

        coEvery { movieRepository.getMovie(movieId) } returns movieModel

        // Act
        viewModel.uiState.test {
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Initial, awaitItem())
            viewModel.getMovie(movieId)
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Loading, awaitItem())
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Movie(movieModel), awaitItem())
        }
    }

    @Test
    fun `getMovie should update uiState to error when repository returns null`() = runTest {
        val movieId = 123
        coEvery { movieRepository.getMovie(movieId) } returns null
        viewModel.uiState.test {
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Initial, awaitItem())
            viewModel.getMovie(movieId)
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Loading, awaitItem())
            assertEquals(
                MovieDetailViewModel.MovieDetailUiState.Error("Movie not found"),
                awaitItem()
            )
        }
    }

    @Test
    fun `getMovie should update uiState to error when repository throws exception`() = runTest {
        val movieId = 123
        val errorMessage = "An error occurred"
        coEvery { movieRepository.getMovie(movieId) } throws Exception(errorMessage)
        viewModel.uiState.test {
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Initial, awaitItem())
            viewModel.getMovie(movieId)
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Loading, awaitItem())
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Error(errorMessage), awaitItem())
        }
    }

    @Test
    fun `loadMovieWatchlistStatus should update isInWatchList when repository returns true`() =
        runTest {
            val movieId = 123
            coEvery { movieRepository.isMovieInWatchList(movieId) } returns true
            viewModel.isInWatchList.test {
                assertEquals(false, awaitItem()) // Initial state
                viewModel.loadMovieWatchlistStatus(movieId)
                assertEquals(true, awaitItem())
            }
        }

    @Test
    fun `loadMovieWatchlistStatus should update isInWatchList when repository returns false`() =
        runTest {
            val movieId = 12
            coEvery { movieRepository.isMovieInWatchList(movieId) } returns false
            viewModel.isInWatchList.test {
                assertEquals(false, awaitItem()) // Initial state
                viewModel.loadMovieWatchlistStatus(movieId)
                assertEquals(false, awaitItem())
            }
        }

    @Test
    fun `addToWatchList adds movie and updates states successfully`() = runTest {
        val movieId = 123

        coEvery { movieRepository.isMovieInWatchList(movieId) } returns false
        coEvery {
            movieRepository.manageMovieWatchList(
                ManageWatchList(
                    "movie",
                    movieId,
                    true
                )
            )
        } returns ManageWatchListResponse(true, 1, "Success")
        coEvery { movieRepository.addMovieToWatchList(movieId) } returns Unit

        // Act and Assert
        viewModel.isWatchListLoaderActive.test {
            assertEquals(false, awaitItem())
            viewModel.isInWatchList.test {
                assertEquals(false, awaitItem())
                viewModel.addToWatchList(movieId)
                assertEquals(true, awaitItem())
            }
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())
        }
    }

    @Test
    fun `addToWatchList removes movie and updates states successfully`() = runTest {
        val movieId = 123
        coEvery { movieRepository.isMovieInWatchList(movieId) } returns true
        coEvery {
            movieRepository.manageMovieWatchList(
                ManageWatchList(
                    "movie",
                    movieId,
                    false
                )
            )
        } returns ManageWatchListResponse(true, 1, "Success")

        viewModel.isWatchListLoaderActive.test {
            assertEquals(false, awaitItem())
            viewModel.isInWatchList.test {
                assertEquals(false, awaitItem())
                viewModel.loadMovieWatchlistStatus(movieId)
                assertEquals(true, awaitItem())
                viewModel.addToWatchList(movieId)
                assertEquals(false, awaitItem())
            }
            assertEquals(true, awaitItem())
            assertEquals(false, awaitItem())
        }
    }

    @Test
    fun `addToWatchList updates uiState to Error when repository fails`() = runTest {
        val movieId = 123
        coEvery { movieRepository.isMovieInWatchList(movieId) } returns false
        coEvery { movieRepository.manageMovieWatchList(ManageWatchList("movie", movieId, true)) } returns ManageWatchListResponse(false, 404, "Error")

        viewModel.uiState.test {
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Initial, awaitItem())
            viewModel.isWatchListLoaderActive.test {
                assertEquals(false, awaitItem())
                viewModel.addToWatchList(movieId)
                assertEquals(true, awaitItem())
                assertEquals(false, awaitItem())
            }
            assertEquals(MovieDetailViewModel.MovieDetailUiState.Error("Failed to update watchlist"), awaitItem())
        }
    }
}
