package com.muthiani.movieswatchpro.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
import com.muthiani.movieswatchpro.data.local.RemoteKeysEntity
import com.muthiani.movieswatchpro.data.mapper.toMovieEntity
import javax.inject.Inject
import timber.log.Timber

@OptIn(ExperimentalPagingApi::class)
class MoviesRemoteMediator
@Inject
constructor(
    val api: MoviesWatchApi,
    val moviesWatchDatabase: MoviesWatchDatabase,
    private val apiType: String,
) : RemoteMediator<Int, MovieEntity>() {
    private val REMOTE_KEY_ID = "movie"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        return try {
            val loadKey =
                when (loadType) {
                    LoadType.REFRESH -> null
                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                    LoadType.APPEND -> {
                        val remoteKey =
                            moviesWatchDatabase.withTransaction {
                                moviesWatchDatabase.remoteKeysDao().remoteKeyByQuery(REMOTE_KEY_ID)
                            }
                        if (remoteKey?.nextKey == null || remoteKey.nextKey == 0) {
                            return MediatorResult.Success(endOfPaginationReached = true)
                        }
                        remoteKey.nextKey
                    }
                }

            val apiResponse =
                when (apiType) {
                    "popular" -> api.getPopular(page = loadKey ?: 1)
                    "now_playing" -> api.getNowShowing(page = loadKey ?: 1)
                    else -> api.getUpcoming(page = loadKey ?: 1)
                }

            val results = apiResponse.results ?: apiResponse.data ?: emptyList()
            val nextPage = apiResponse.page + 1

            try {
                moviesWatchDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        Timber.d("MoviesRemoteMediator Clearing old data")
                        moviesWatchDatabase.remoteKeysDao().clearAll()
                        moviesWatchDatabase.moviesDao().clearAll()
                    }

                    moviesWatchDatabase.moviesDao().insertMovies(results.map { it.toMovieEntity() })

                    moviesWatchDatabase.remoteKeysDao().insertOrReplace(RemoteKeysEntity(REMOTE_KEY_ID, nextPage))
                }
            } catch (e: Exception) {
                Timber.d("MoviesRemoteMediator Error: ${e.message}")
            }

            val endOfPaginationReached = apiResponse.page >= apiResponse.total_pages
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            Timber.d("MoviesRemoteMediator Error: ${e.message}")
            MediatorResult.Error(e)
        }
    }
}
