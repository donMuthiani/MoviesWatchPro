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

        fun getNowShowingMovies() {
            _uiState.value = DiscoverUiState.Loading

            viewModelScope.launch {
                val nowShowingMoviesDeferred = async { fakeWatchListRepository.getNowShowingMovies() }
                val nowShowing = nowShowingMoviesDeferred.await()

                _uiState.value = DiscoverUiState.NowShowing(nowShowing.data ?: emptyList())
            }
        }

        sealed class DiscoverUiState {
            data object Initial : DiscoverUiState()

            data object Loading : DiscoverUiState()

            data class NowShowing(val nowShowingList: List<MovieModel>) : DiscoverUiState()
        }
    }
