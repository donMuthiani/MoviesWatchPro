package com.muthiani.movieswatchpro.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muthiani.movieswatchpro.data.FakeWatchListRepository
import com.muthiani.movieswatchpro.data.Movie
import com.muthiani.movieswatchpro.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
    @Inject
    constructor(private val fakeWatchListRepository: FakeWatchListRepository) : ViewModel() {
        private val _uiState: MutableStateFlow<DiscoverUiState> = MutableStateFlow(DiscoverUiState.Initial)
        val uiState: StateFlow<DiscoverUiState> = _uiState.asStateFlow()

        init {
            getNowShowingMovies()
        }

        suspend fun getMovie(movieId: Int): Movie? {
            return fakeWatchListRepository.getMovie(movieId)
        }

        private fun getNowShowingMovies() {
            _uiState.value = DiscoverUiState.Loading

            viewModelScope.launch {
                try {
                    val nowShowingMoviesDeferred = async { fakeWatchListRepository.getNowShowingMovies() }
                    val popularDeferred = async { fakeWatchListRepository.getPopularMovies() }
                    val topRatedDeferred = async { fakeWatchListRepository.getTopRatedMovies() }
                    val upcomingDeferred = async { fakeWatchListRepository.getUpcomingMovies() }
                    val trendingDeferred = async { fakeWatchListRepository.getTrendingMovies() }

                    val nowShowing = nowShowingMoviesDeferred.await()
                    val popular = popularDeferred.await()
                    val topRated = topRatedDeferred.await()
                    val upcoming = upcomingDeferred.await()
                    val trending = trendingDeferred.await()

                    _uiState.value =
                        DiscoverUiState.Success(
                            nowShowing = nowShowing.data ?: emptyList(),
                            popular = popular.data ?: emptyList(),
                            topRated = topRated.data ?: emptyList(),
                            trending = trending.data ?: emptyList(),
                            upcoming = upcoming.data ?: emptyList(),
                        )
                } catch (e: Exception) {
                    _uiState.value = DiscoverUiState.Error(e.message ?: "An error occurred")
                }
            }
        }

        sealed class DiscoverUiState {
            data object Initial : DiscoverUiState()

            data object Loading : DiscoverUiState()

            data class Success(
                val nowShowing: List<MovieModel>,
                val popular: List<MovieModel>,
                val topRated: List<MovieModel>,
                val trending: List<MovieModel>,
                val upcoming: List<MovieModel>,
            ) : DiscoverUiState()

            data class Error(val message: String) : DiscoverUiState()
        }
    }
