package com.muthiani.movieswatchpro.presentation.detail

import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muthiani.movieswatchpro.LocalNavAnimatedVisibilityScope
import com.muthiani.movieswatchpro.LocalSharedTransitionScope
import com.muthiani.movieswatchpro.MainActivity
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MovieDetailKtTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: MovieDetailViewModel
    private var navController: NavController? = null

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = mockk(relaxed = true)

        every { viewModel.uiState } returns MutableStateFlow(MovieDetailViewModel.MovieDetailUiState.Initial)
        every { viewModel.isInWatchList } returns MutableStateFlow(false)
        every { viewModel.isWatchListLoaderActive } returns MutableStateFlow(false)
    }

    @Test
    fun movieDetailScreenShowsLoadingState() {
        every { viewModel.uiState } returns MutableStateFlow(MovieDetailViewModel.MovieDetailUiState.Loading)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setContent {
                MoviesWatchProTheme {
                    TestNavHost(
                        movieId = 1,
                        viewModel = viewModel,
                        onNavControllerCreated = { navController = it }
                    )
                }
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()
    }

    @Test
    fun movieDetailScreenShowsMovieDetailsWhenLoaded() {
        val movieModel = MovieModel(
            id = 1,
            title = "Test Movie",
            overview = "This is a test movie",
            posterPath = "/test_poster.jpg",
            backdropPath = "/test_backdrop.jpg",
            releaseDate = "2023-10-10",
            voteAverage = 7.5
        )

        every { viewModel.uiState } returns MutableStateFlow(
            MovieDetailViewModel.MovieDetailUiState.Movie(
                movieModel
            )
        )
        every { viewModel.isInWatchList } returns MutableStateFlow(true)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setContent {
                MoviesWatchProTheme {
                    TestNavHost(
                        movieId = 1,
                        viewModel = viewModel,
                        onNavControllerCreated = { navController = it })
                }
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Test Movie").assertIsDisplayed()
        composeTestRule.onNodeWithText("This is a test movie").assertIsDisplayed()
        composeTestRule.onNodeWithText("${movieModel.voteAverage} · ").assertIsDisplayed()
        composeTestRule.onNodeWithText("Remove from watchlist", ignoreCase = true).assertIsDisplayed()
    }

    @Test
    fun movieDetailScreenShowsErrorState() {
        val errorMessage = "An error occurred"
        every { viewModel.uiState } returns MutableStateFlow(MovieDetailViewModel.MovieDetailUiState.Error(errorMessage))

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setContent {
                MoviesWatchProTheme {
                    TestNavHost(
                        movieId = 1,
                        viewModel = viewModel,
                        onNavControllerCreated = { navController = it })
                }
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("An error occurred").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Loading").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("MovieDetailScreen").assertDoesNotExist()
    }

    @Test
    fun watchListButtonTogglesStateCorrectly() {
        val movie = MovieModel(
            id = 1,
            title = "Test Movie",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            overview = "This is a test movie overview.",
            releaseDate = "2023-10-10",
            voteAverage = 7.5
        )

        val isInWatchListFlow = MutableStateFlow(false)

        every { viewModel.uiState } returns MutableStateFlow(MovieDetailViewModel.MovieDetailUiState.Movie(movie))
        every { viewModel.isInWatchList } returns isInWatchListFlow

        every { viewModel.addToWatchList(movie.id) } answers {
            isInWatchListFlow.value = !isInWatchListFlow.value
        }

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setContent {
                MoviesWatchProTheme {
                    TestNavHost(
                        movieId = 1,
                        viewModel = viewModel,
                        onNavControllerCreated = { navController = it })
                }
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Add to watchlist", ignoreCase = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("Toggle watchlist").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Remove from watchlist", ignoreCase = true).assertIsDisplayed()

        verify { viewModel.addToWatchList(movie.id) }
    }

    @Test
    fun backButtonNavigatesBack() {
        val movie = MovieModel(
            id = 1,
            title = "Test Movie",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            overview = "This is a test movie overview.",
            releaseDate = "2023-10-10",
            voteAverage = 7.5
        )

        every { viewModel.uiState } returns MutableStateFlow(MovieDetailViewModel.MovieDetailUiState.Movie(movie))

        every { viewModel.isInWatchList } returns MutableStateFlow(false)

        // Mock upPress lambda
        val upPress = mockk<() -> Unit>(relaxed = true)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.setContent {
                MoviesWatchProTheme {
                    TestNavHost(
                        movieId = 1,
                        viewModel = viewModel,
                        onNavControllerCreated = { navController = it })
                }
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("backButton").assertIsDisplayed()

        composeTestRule.onNodeWithTag("backButton").performClick()

        composeTestRule.waitForIdle()

        verify {
            upPress.invoke()
        }
    }

    @After
    fun tearDown() {
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TestNavHost(
    movieId: Long,
    viewModel: MovieDetailViewModel,
    onNavControllerCreated: (NavController) -> Unit
) {
    val navController = rememberNavController()
    onNavControllerCreated(navController)

    SharedTransitionLayout { ->
        CompositionLocalProvider(value = LocalSharedTransitionScope provides this) {
            AnimatedVisibility(visible = true) {
                CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                    NavHost(
                        navController = navController,
                        startDestination = "movie/$movieId"
                    ) {
                        composable("movie/{movieId}") {
                            MovieDetailScreen(
                                movieId = movieId,
                                upPress = { navController.popBackStack() }, // Handle back navigation
                                movieDetailViewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
