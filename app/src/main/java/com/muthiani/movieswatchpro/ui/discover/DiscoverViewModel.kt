package com.muthiani.movieswatchpro.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muthiani.movieswatchpro.data.MovieRepository
import com.muthiani.movieswatchpro.models.MovieCollection
import com.muthiani.movieswatchpro.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
    @Inject
    constructor(private val movieRepository: MovieRepository) : ViewModel() {
        private val _uiState: MutableStateFlow<DiscoverUiState> = MutableStateFlow(DiscoverUiState.Initial)
        val uiState: StateFlow<DiscoverUiState> = _uiState.asStateFlow()

        private val exceptionHandler =
            CoroutineExceptionHandler { _, exception ->
                _uiState.value = DiscoverUiState.Error(exception.message ?: "An error occurred")
            }

        init {
            getMovies()
        }

        fun getMovies() {
            _uiState.value = DiscoverUiState.Loading

            viewModelScope.launch(exceptionHandler) {
                try {
                    val (nowShowing, popular, topRated, upcoming, trending) =
                        listOf(
                            async { movieRepository.getNowShowingMovies() },
                            async { movieRepository.getPopularMovies() },
                            async { movieRepository.getTopRatedMovies() },
                            async { movieRepository.getUpcomingMovies() },
                            async { movieRepository.getTrendingMovies() },
                        ).map { it.await() }

                    _uiState.value =
                        DiscoverUiState.Success(
                            collection =
                                listOf(
                                    MovieCollection(name = "Now Showing", movies = nowShowing.data ?: emptyList()),
                                    MovieCollection(name = "Popular", movies = popular.data ?: emptyList()),
                                    MovieCollection(name = "Trending", movies = trending.data ?: emptyList()),
                                    MovieCollection(name = "Top Rated", movies = topRated.data ?: emptyList()),
                                    MovieCollection(name = "Upcoming", movies = upcoming.data ?: emptyList()),
                                ),
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
                val collection: List<MovieCollection>,
            ) : DiscoverUiState()

            data class Error(val message: String) : DiscoverUiState()
        }
    }
