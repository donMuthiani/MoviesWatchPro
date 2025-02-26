package com.muthiani.movieswatchpro.presentation.view_more

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MovieListViewViewModel
    @Inject
    constructor(val movieRepository: MovieRepository, val moviesWatchDatabase: MoviesWatchDatabase) : ViewModel() {
        private val _uiState: MutableStateFlow<MovieListViewerUiState> =
            MutableStateFlow(
                MovieListViewerUiState.Initial,
            )
        val uiState: StateFlow<MovieListViewerUiState> = _uiState.asStateFlow()

        @OptIn(ExperimentalPagingApi::class)
        fun getMovies(
            apiCallType: String,
            today: String,
        ) {
            _uiState.value = MovieListViewerUiState.Loading
            val query = apiCallType
//
//            viewModelScope.launch {
//                val pagingDataFLow =
//                    Pager(
//                        config = PagingConfig(pageSize = 20),
//                        remoteMediator =
//                            MovieListRemoteMediator(
//                                query = query,
//                                repository = movieRepository,
//                                moviesWatchDatabase = moviesWatchDatabase,
//                                apiCallType = apiCallType,
//                            ),
//                        pagingSourceFactory = {
// //                            Timber.tag("PagingSource").i("MoviesPaging PagingSource initialized for query: $query")
//                            moviesWatchDatabase.moviesDao().pagingSource()
//                        },
//                    ).flow.cachedIn(viewModelScope)
// //                pagingDataFLow.collectLatest {
//                    _uiState.value = MovieListViewerUiState.Success(
//                        pagingDataFLow
//                    )
// //                }
//            }
        }

        sealed class MovieListViewerUiState {
            data object Initial : MovieListViewerUiState()

            data object Loading : MovieListViewerUiState()

            data class Success(
                val collection: Flow<PagingData<MovieModel>>,
            ) : MovieListViewerUiState()

            data class Error(val message: String) : MovieListViewerUiState()
        }
    }
