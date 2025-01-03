package com.muthiani.movieswatchpro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muthiani.movieswatchpro.data.MovieRepository
import com.muthiani.movieswatchpro.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel
    @Inject
    constructor(private val fakeWatchListRepository: MovieRepository) : ViewModel() {
        private val _uiState: MutableStateFlow<WatchListUiState> = MutableStateFlow(WatchListUiState.Initial)
        val uiState: StateFlow<WatchListUiState> = _uiState.asStateFlow()

        private val exceptionHandler =
            CoroutineExceptionHandler { _, exception ->
                _uiState.value = WatchListUiState.Error(exception.message ?: "An error occurred")
            }

        init {
            getWatchList()
        }

        private fun getWatchList() {
            viewModelScope.launch(exceptionHandler) {
                _uiState.value = WatchListUiState.Loading(show = true)
                val result = fakeWatchListRepository.getWatchList()
                _uiState.value = WatchListUiState.WatchList(result.results.orEmpty())
            }
        }

        sealed class WatchListUiState {
            data object Initial : WatchListUiState()

            data class Loading(val show: Boolean) : WatchListUiState()

            data class WatchList(val watchList: List<MovieModel>) : WatchListUiState()

            data class Error(val message: String) : WatchListUiState()
        }
    }
