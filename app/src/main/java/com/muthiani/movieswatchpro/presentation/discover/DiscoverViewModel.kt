package com.muthiani.movieswatchpro.presentation.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.useCase.GetMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel
    @Inject
    constructor(
        getMovies: GetMovies,
    ) : ViewModel() {
        val popularMoviesPagingDataFlow: Flow<PagingData<MovieModel>> = getMovies.popular().cachedIn(viewModelScope)
        val upcomingMoviesPagingDataFlow: Flow<PagingData<MovieModel>> = getMovies.upcoming().cachedIn(viewModelScope)
        val nowShowingMoviesPagingDataFlow: Flow<PagingData<MovieModel>> = getMovies.nowPlaying().cachedIn(viewModelScope)
        val viewMoreMoviesPagingDataFlow: Flow<PagingData<MovieModel>> = getMovies.viewMore().cachedIn(viewModelScope)
    }
