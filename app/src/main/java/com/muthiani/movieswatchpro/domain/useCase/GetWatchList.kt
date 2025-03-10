package com.muthiani.movieswatchpro.domain.useCase

import androidx.paging.PagingData
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetWatchList
@Inject constructor(private val movieRepository: MovieRepository) {
    fun getWatchList(): Flow<PagingData<MovieModel>> = movieRepository.getWatchList().flowOn(Dispatchers.IO)
}
