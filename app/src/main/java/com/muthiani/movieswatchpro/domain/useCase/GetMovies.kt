package com.muthiani.movieswatchpro.domain.useCase

import androidx.paging.PagingData
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetMovies
    @Inject
    constructor(private val repository: MovieRepository) {
        fun popular(): Flow<PagingData<MovieModel>> = repository.getPopularMovies().flowOn(Dispatchers.IO)

        fun upcoming(): Flow<PagingData<MovieModel>> = repository.getUpcomingMovies().flowOn(Dispatchers.IO)

        fun nowPlaying(): Flow<PagingData<MovieModel>> = repository.getNowShowingMovies().flowOn(Dispatchers.IO)

        fun viewMore(): Flow<PagingData<MovieModel>> = repository.getMoreMovies().flowOn(Dispatchers.IO)
    }
