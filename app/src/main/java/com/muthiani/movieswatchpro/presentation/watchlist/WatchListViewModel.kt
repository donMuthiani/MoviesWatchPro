package com.muthiani.movieswatchpro.presentation.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.useCase.GetWatchList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel
    @Inject
    constructor(
        getWatchList: GetWatchList,
    ) : ViewModel() {
        val watchListMoviesPagingDataFlow: Flow<PagingData<MovieModel>> = getWatchList.getWatchList().cachedIn(viewModelScope)
    }
