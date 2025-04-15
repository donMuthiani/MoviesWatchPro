package com.muthiani.movieswatchpro.presentation.detail

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muthiani.movieswatchpro.MainActivity
import com.muthiani.movieswatchpro.presentation.theme.MoviesWatchProTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
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

    @After
    fun tearDown() {
    }
}

@Composable
fun TestNavHost(
    movieId: Long,
    viewModel: MovieDetailViewModel,
    onNavControllerCreated: (NavController) -> Unit
) {
    val navController = rememberNavController()
    onNavControllerCreated(navController)

    NavHost(
        navController = navController,
        startDestination = "movie/$movieId"
    ) {
        composable("movie/{movieId}") {
            MovieDetailScreen(
                movieId = movieId,
                upPress = { navController.popBackStack() },
                movieDetailViewModel = viewModel
            )
        }
    }
}
