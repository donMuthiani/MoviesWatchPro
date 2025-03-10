package com.muthiani.movieswatchpro.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MovieEntityWatchList
import com.muthiani.movieswatchpro.data.mapper.toMovieModel
import com.muthiani.movieswatchpro.data.remote.ApiConstants
import com.muthiani.movieswatchpro.data.remote.MoviesWatchApi
import com.muthiani.movieswatchpro.domain.entity.ManageWatchList
import com.muthiani.movieswatchpro.domain.entity.ManageWatchListResponse
import com.muthiani.movieswatchpro.domain.entity.MovieModel
import com.muthiani.movieswatchpro.domain.entity.WatchListResponse
import com.muthiani.movieswatchpro.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class MovieRepositoryImpl
@Inject
constructor(
    @Named("popular") private val popularMoviesPager: Pager<Int, MovieEntity>,
    @Named("upcoming") private val upcomingMoviesPager: Pager<Int, MovieEntity>,
    @Named("now_showing") private val nowShowingMoviesPager: Pager<Int, MovieEntity>,
    @Named("view_more") private val viewMoreMoviesPager: Pager<Int, MovieEntity>,
    @Named("watchlist") private val watchListMoviesPager: Pager<Int, MovieEntityWatchList>,
    private val moviesWatchApi: MoviesWatchApi,
) : MovieRepository {
    override fun getWatchList(page: Int): Flow<PagingData<MovieModel>> {
        return watchListMoviesPager.flow.map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }
    }

    override suspend fun getMovie(movieId: Int): MovieModel {
        return moviesWatchApi.getMovieDetail(movieId)
    }

    override fun getNowShowingMovies(page: Int): Flow<PagingData<MovieModel>> {
        return nowShowingMoviesPager.flow.map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }
    }

    override fun getPopularMovies(page: Int): Flow<PagingData<MovieModel>> {
        return popularMoviesPager.flow.map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }
    }

    override suspend fun manageMovieWatchList(manageWatchList: ManageWatchList): ManageWatchListResponse {
        return moviesWatchApi.manageWatchList(ApiConstants.ACCOUNT_ID, manageWatchList)
    }

    override fun getUpcomingMovies(page: Int): Flow<PagingData<MovieModel>> {
        return upcomingMoviesPager.flow.map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }
    }

    override fun getMoreMovies(page: Int): Flow<PagingData<MovieModel>> {
        return viewMoreMoviesPager.flow.map { pagingData ->
            pagingData.map { it.toMovieModel() }
        }
    }
}
