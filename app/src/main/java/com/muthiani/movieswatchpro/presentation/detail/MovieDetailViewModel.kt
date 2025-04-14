package com.muthiani.movieswatchpro.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muthiani.movieswatchpro.domain.entity.ManageWatchList
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MovieDetailViewModel
@Inject
constructor(private val movieRepository: MovieRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<MovieDetailUiState> =
        MutableStateFlow(
            MovieDetailUiState.Initial,
        )
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    val _isWatchListLoaderActive = MutableStateFlow(false)
    var isWatchListLoaderActive: StateFlow<Boolean> = _isWatchListLoaderActive

    val _isInWatchList = MutableStateFlow(false)
    val isInWatchList: StateFlow<Boolean> = _isInWatchList.asStateFlow()

    fun loadMovieWatchlistStatus(movieId: Int) {
        viewModelScope.launch {
            _isInWatchList.value =
                withContext(Dispatchers.IO) { movieRepository.isMovieInWatchList(movieId) }
        }
    }

    // Add for testing
    fun setWatchListStateForTesting(isInWatchList: Boolean) {
        _isInWatchList.value = isInWatchList
    }

    private val exceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _uiState.value = MovieDetailUiState.Error(exception.message ?: "An error occurred")
        }

    fun getMovie(movieId: Int) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { MovieDetailUiState.Loading }

            val movie = withContext(Dispatchers.IO) { movieRepository.getMovie(movieId) }

            if (movie != null) {
                _uiState.update { MovieDetailUiState.Movie(movie) }
            } else {
                _uiState.update { MovieDetailUiState.Error("Movie not found") }
            }
        }
    }

    fun addToWatchList(id: Int) {
        _isWatchListLoaderActive.value = true
        val shouldAdd = !_isInWatchList.value
        viewModelScope.launch(exceptionHandler) {
            val response =
                withContext(Dispatchers.IO) {
                    movieRepository.manageMovieWatchList(
                        ManageWatchList(media_id = id, watchlist = shouldAdd),
                    )
                }
            if (response.success) {
                _isInWatchList.value = shouldAdd
                _isWatchListLoaderActive.value = false
                withContext(Dispatchers.IO) {
                    if (shouldAdd) {
                        movieRepository.addMovieToWatchList(id)
                    } else {
                        movieRepository.removeMovieFromWatchList(id)
                    }
                }
            } else {
                _uiState.value = MovieDetailUiState.Error("Failed to update watchlist")
            }
            _isWatchListLoaderActive.value = false
        }
    }

    sealed class MovieDetailUiState {
        data class Error(val message: String) : MovieDetailUiState()

        data object Initial : MovieDetailUiState()

        data object Loading : MovieDetailUiState()

        data class Movie(val movieModel: MovieModel) : MovieDetailUiState()
    }
}
