package com.muthiani.movieswatchpro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muthiani.movieswatchpro.data.MovieRepository
import com.muthiani.movieswatchpro.data.Movie
import com.muthiani.movieswatchpro.data.successOr
import com.muthiani.movieswatchpro.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel
    @Inject
    constructor(private val fakeWatchListRepository: MovieRepository) : ViewModel() {
        private val _uiState = MutableStateFlow(WatchListUiState(loading = true))
        val uiState: StateFlow<WatchListUiState> = _uiState.asStateFlow()

        data class WatchListUiState(
            val loading: Boolean = false,
            val watchList: List<Movie> = emptyList(),
            val error: String? = null,
        )

        init {
            refreshAll()
        }

        private fun refreshAll() {
            _uiState.update { it.copy(loading = true) }

            viewModelScope.launch {
                val watchListDeferred = async { fakeWatchListRepository.getWatchList() }

                val watchList = watchListDeferred.await().successOr(emptyList())

                _uiState.update {
                    it.copy(loading = false, watchList = watchList)
                }
            }
        }

        suspend fun getMovie(movieId: Int): MovieModel? {
            return fakeWatchListRepository.getMovie(movieId)
        }

        sealed class DiscoverUiState {
            data class NowShowing(val nowShowingList: List<MovieModel>) : DiscoverUiState()
        }
    }
