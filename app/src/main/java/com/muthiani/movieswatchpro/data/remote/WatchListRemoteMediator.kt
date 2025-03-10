package com.muthiani.movieswatchpro.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.muthiani.movieswatchpro.data.local.MovieEntity
import com.muthiani.movieswatchpro.data.local.MovieEntityWatchList
import com.muthiani.movieswatchpro.data.local.MoviesWatchDatabase
import com.muthiani.movieswatchpro.data.local.RemoteKeysEntity
import com.muthiani.movieswatchpro.data.local.RemoteKeysWatchList
import com.muthiani.movieswatchpro.data.mapper.toMovieEntity
import com.muthiani.movieswatchpro.data.mapper.toMovieEntityWatchList
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class WatchListRemoteMediator
@Inject
constructor(
    val api: MoviesWatchApi,
    val moviesWatchDatabase: MoviesWatchDatabase,
) : RemoteMediator<Int, MovieEntityWatchList>() {
    private val REMOTE_KEY_ID = "watchList"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntityWatchList>,
    ): MediatorResult {
        return try {
            val loadKey =
                when (loadType) {
                    LoadType.REFRESH -> null
                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                    LoadType.APPEND -> {
                        val remoteKey =
                            moviesWatchDatabase.withTransaction {
                                moviesWatchDatabase.remoteKeysDao().remoteWatchListKeyByQuery(REMOTE_KEY_ID)
                            }
                        if (remoteKey.nextKey == null || remoteKey.nextKey == 0) {
                            return MediatorResult.Success(endOfPaginationReached = true)
                        }
                        remoteKey.nextKey
                    }
                }
            val apiResponse = api.getWatchList(page = loadKey ?: 1)
            val results = apiResponse.results ?: apiResponse.data ?: emptyList()
            val nextPage = apiResponse.page + 1

            moviesWatchDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesWatchDatabase.remoteKeysDao().clearAllWatchListKeys()
                    moviesWatchDatabase.moviesDao().clearAllWatchList()
                }

                moviesWatchDatabase.moviesDao().insertWatchListMovies(results.map { it.toMovieEntityWatchList() })

                moviesWatchDatabase.remoteKeysDao().insertOrReplaceWatchList(RemoteKeysWatchList(REMOTE_KEY_ID, nextPage))
            }
            val endOfPaginationReached = apiResponse.page >= apiResponse.total_pages
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
