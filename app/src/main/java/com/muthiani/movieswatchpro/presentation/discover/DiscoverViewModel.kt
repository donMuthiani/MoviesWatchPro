package com.muthiani.movieswatchpro.presentation.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muthiani.movieswatchpro.domain.entity.MovieCollection
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.useCase.GetPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
    @Inject
    constructor(
        getPopularMovies: GetPopularMovies,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<DiscoverUiState> = MutableStateFlow(DiscoverUiState.Initial)
        val uiState: StateFlow<DiscoverUiState> = _uiState.asStateFlow()

        private val exceptionHandler =
            CoroutineExceptionHandler { _, exception ->
                _uiState.value = DiscoverUiState.Error(exception.message ?: "An error occurred")
            }

        val popularMoviesPagingDataFlow: Flow<PagingData<MovieModel>> = getPopularMovies().cachedIn(viewModelScope)

//    init {
//        getMovies()
//    }

        @OptIn(ExperimentalPagingApi::class)
        fun getMovies() {
//            _uiState.value = DiscoverUiState.Loading
//
//            viewModelScope.launch(exceptionHandler) {
//                try {
//                    val moviesWatchDao = moviesWatchDatabase.moviesDao()
//                    val pagingDataFlow: Flow<PagingData<MovieModel>> =
//                        Pager(
//                            config = PagingConfig(pageSize = 20),
//                            remoteMediator =
//                                DiscoverRemoteMediator(
//                                    query = "",
//                                    repository = movieRepository,
//                                    moviesWatchDatabase = moviesWatchDatabase,
//                                ),
//                        ) {
//                            moviesWatchDao.getPopularPagingSource()
//                        }.flow
//                            .cachedIn(viewModelScope)
//
//                    val nowShowingPagingDataFlow: Flow<PagingData<MovieModel>> =
//                        Pager(
//                            config = PagingConfig(20),
//                            remoteMediator =
//                                NowShowingRemoteMediator(
//                                    query = "",
//                                    repository = movieRepository,
//                                    moviesWatchDatabase = moviesWatchDatabase,
//                                ),
//                        ) {
//                            moviesWatchDao.getNowShowingPagingSource()
//                        }.flow.cachedIn(viewModelScope)
//
//                    val upcomingPagingDataFlow: Flow<PagingData<MovieModel>> =
//                        Pager(
//                            config = PagingConfig(pageSize = 20),
//                            remoteMediator =
//                                UpcomingRemoteMediator(
//                                    query = "",
//                                    repository = movieRepository,
//                                    moviesWatchDatabase = moviesWatchDatabase,
//                                ),
//                        ) {
//                            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//                            val today = dateFormat.format(Date())
//                            moviesWatchDao.getUpcomingPagingSource(today)
//                        }.flow
//                            .cachedIn(viewModelScope)
//
//                    _uiState.value =
//                        DiscoverUiState.Success(
//                            collection =
//                            listOf(
//                                MovieCollection(
//                                    name = "NOW_SHOWING",
//                                    movies = nowShowingPagingDataFlow
//                                ),
//                                MovieCollection(name = "POPULAR", movies = pagingDataFlow),
//                                MovieCollection(name = "UPCOMING", movies = upcomingPagingDataFlow),
//                            ),
//                        )
//                } catch (e: Exception) {
//                    _uiState.value = DiscoverUiState.Error(e.message ?: "An error occurred")
//                }
//            }
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
