package com.muthiani.movieswatchpro.ui.list_viewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muthiani.movieswatchpro.data.ApiCallType
import com.muthiani.movieswatchpro.data.MovieRepository
import com.muthiani.movieswatchpro.data.pagination.MovieListRemoteMediator
import com.muthiani.movieswatchpro.db.MoviesWatchDatabase
import com.muthiani.movieswatchpro.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MovieListViewViewModel
    @Inject
    constructor(val movieRepository: MovieRepository, val moviesWatchDatabase: MoviesWatchDatabase) : ViewModel() {
        private val _uiState: MutableStateFlow<MovieListViewerUiState> = MutableStateFlow(MovieListViewerUiState.Initial)
        val uiState: StateFlow<MovieListViewerUiState> = _uiState.asStateFlow()

        @OptIn(ExperimentalPagingApi::class)
        fun getMovies(
            apiCallType: String,
            today: String,
        ) {
            _uiState.value = MovieListViewerUiState.Loading

            viewModelScope.launch {
                val pagingDataFLow =
                    Pager(
                        config = PagingConfig(pageSize = 20),
                        remoteMediator =
                            MovieListRemoteMediator(
                                query = Random.nextInt(100).toString(),
                                repository = movieRepository,
                                moviesWatchDatabase = moviesWatchDatabase,
                                apiCallType = apiCallType,
                            ),
                        pagingSourceFactory = {
                            when (apiCallType) {
                                ApiCallType.NOW_SHOWING.name -> moviesWatchDatabase.moviesDao().getNowShowingPagingSource()
                                ApiCallType.POPULAR.name -> moviesWatchDatabase.moviesDao().getPopularPagingSource()
                                else -> moviesWatchDatabase.moviesDao().getUpcomingPagingSource(today)
                            }
                        },
                    ).flow.cachedIn(viewModelScope)

                pagingDataFLow.collectLatest {
                    _uiState.value = MovieListViewerUiState.Success(pagingDataFLow)
                }
            }
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
