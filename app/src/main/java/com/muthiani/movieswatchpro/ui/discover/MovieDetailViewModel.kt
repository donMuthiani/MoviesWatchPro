package com.muthiani.movieswatchpro.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muthiani.movieswatchpro.data.FakeWatchListRepository
import com.muthiani.movieswatchpro.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel
    @Inject
    constructor(private val fakeWatchListRepository: FakeWatchListRepository) : ViewModel() {
        private val _uiState: MutableStateFlow<MovieDetailUiState> = MutableStateFlow(MovieDetailUiState.Initial)
        val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

        private val exceptionHandler =
            CoroutineExceptionHandler { _, exception ->
                _uiState.value = MovieDetailUiState.Error(exception.message ?: "An error occurred")
            }

        suspend fun getMovie(movieId: Int) {
            viewModelScope.launch(exceptionHandler) {
                _uiState.value = MovieDetailUiState.Loading

                val movie = withContext(Dispatchers.IO) { fakeWatchListRepository.getMovie(movieId) }

                if (movie != null) {
                    _uiState.value = MovieDetailUiState.Movie(movie)
                } else {
                    _uiState.value = MovieDetailUiState.Error("Movie not found")
                }
            }
        }

        sealed class MovieDetailUiState {
            data class Error(val message: String) : MovieDetailUiState()

            data object Initial : MovieDetailUiState()

            data object Loading : MovieDetailUiState()

            data class Movie(val movieModel: MovieModel) : MovieDetailUiState()
        }
    }
