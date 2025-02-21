package com.muthiani.movieswatchpro.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muthiani.movieswatchpro.data.MovieRepository
import com.muthiani.movieswatchpro.data.pagination.DiscoverRemoteMediator
import com.muthiani.movieswatchpro.data.pagination.NowShowingRemoteMediator
import com.muthiani.movieswatchpro.data.pagination.UpcomingRemoteMediator
import com.muthiani.movieswatchpro.db.MoviesWatchDatabase
import com.muthiani.movieswatchpro.models.MovieCollection
import com.muthiani.movieswatchpro.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
    @Inject
    constructor(private val movieRepository: MovieRepository, private val moviesWatchDatabase: MoviesWatchDatabase) : ViewModel() {
        private val _uiState: MutableStateFlow<DiscoverUiState> = MutableStateFlow(DiscoverUiState.Initial)
        val uiState: StateFlow<DiscoverUiState> = _uiState.asStateFlow()

        private val exceptionHandler =
            CoroutineExceptionHandler { _, exception ->
                _uiState.value = DiscoverUiState.Error(exception.message ?: "An error occurred")
            }

        init {
            getMovies()
        }

        @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
        fun getMovies() {
            _uiState.value = DiscoverUiState.Loading

            viewModelScope.launch(exceptionHandler) {
                try {
                    val moviesWatchDao = moviesWatchDatabase.moviesDao()
                    val pagingDataFlow: Flow<PagingData<MovieModel>> =
                        Pager(
                            config = PagingConfig(pageSize = 20),
                            remoteMediator =
                                DiscoverRemoteMediator(
                                    query = "",
                                    repository = movieRepository,
                                    moviesWatchDatabase = moviesWatchDatabase,
                                ),
                        ) {
                            moviesWatchDao.getPopularPagingSource()
                        }.flow
                            .cachedIn(viewModelScope)

                    val nowShowingPagingDataFlow: Flow<PagingData<MovieModel>> =
                        Pager(
                            config = PagingConfig(20),
                            remoteMediator =
                                NowShowingRemoteMediator(
                                    query = "",
                                    repository = movieRepository,
                                    moviesWatchDatabase = moviesWatchDatabase,
                                ),
                        ) {
                            moviesWatchDao.getNowShowingPagingSource()
                        }.flow.cachedIn(viewModelScope)

                    val upcomingPagingDataFlow: Flow<PagingData<MovieModel>> =
                        Pager(
                            config = PagingConfig(pageSize = 20),
                            remoteMediator =
                                UpcomingRemoteMediator(
                                    query = "",
                                    repository = movieRepository,
                                    moviesWatchDatabase = moviesWatchDatabase,
                                ),
                        ) {
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val today = dateFormat.format(Date())
                            moviesWatchDao.getUpcomingPagingSource(today)
                        }.flow
                            .cachedIn(viewModelScope)

                    _uiState.value =
                        DiscoverUiState.Success(
                            collection =
                                listOf(
                                    MovieCollection(name = "NOW_SHOWING", movies = nowShowingPagingDataFlow),
                                    MovieCollection(name = "POPULAR", movies = pagingDataFlow),
                                    MovieCollection(name = "UPCOMING", movies = upcomingPagingDataFlow),
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
